package com.example.hp_29_MiniatureCrafts.repository.dashboard;

import com.example.hp_29_MiniatureCrafts.entity.POSOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DashboardRepository extends JpaRepository<POSOrder, Long> {

    // note:
    // viết 2 hàm mới là tính tổng doanh thu trong tháng, trong ngày của các đơn đã thanh toán
    // viết thêm đếm tổng các đơn hàng bị hủy trong tháng
    @Query("""
                SELECT MONTH(po.Payment_Time) AS month, 
                       SUM(po.Total_Amount) AS revenue
                FROM POSOrder po
                WHERE YEAR(po.Payment_Time) = :year AND 
                      po.Status = 3
                GROUP BY MONTH(po.Payment_Time)
                ORDER BY MONTH(po.Payment_Time)
            """)
    List<Object[]> getMonthlyRevenue(@Param("year") int year);

    // Doanh thu từng ngày trong tháng của các đơn đã thanh toán (Status = 3)
    @Query("""
              SELECT CAST(po.Payment_Time AS DATE) AS day,
                    COALESCE(SUM(po.Total_Amount), 0) AS revenue
                     FROM POSOrder po
                    WHERE YEAR(po.Payment_Time) = :year
                    AND MONTH(po.Payment_Time) = :month
                    AND po.Status = 3
                GROUP BY CAST(po.Payment_Time AS DATE)
                 ORDER BY CAST(po.Payment_Time AS DATE)
            """)
    List<Object[]> getDailyRevenueInMonth(@Param("year") int year, @Param("month") int month);


    // 1) Top selling products (variation) with limit
    @Query(value = """
            SELECT v.ID AS variationId,
                   v.Name AS variationName,
                   SUM(ol.Quantity) AS totalSold,
                   SUM(ol.Price) AS totalRevenue
            FROM OrderLine ol
            JOIN Variation v ON ol.VariationID = v.ID
            GROUP BY v.ID, v.Name
            ORDER BY SUM(ol.Quantity) DESC
            OFFSET 0 ROWS FETCH NEXT :limit ROWS ONLY
            """, nativeQuery = true)
    List<Object[]> findTopSellingProductsNative(@Param("limit") int limit);

    // Total quantity sold (all time)
    @Query(value = "SELECT COALESCE(SUM(Quantity), 0) FROM OrderLine", nativeQuery = true)
    Object getTotalQuantitySoldNative(); // convert in service

    // Sold quantity per day (date, qty)
    @Query(value = """
            SELECT CONVERT(date, o.Order_Time) AS day,
                   SUM(ol.Quantity) AS qty
            FROM OrderLine ol
            JOIN POSOder o ON ol.OderID = o.ID
            GROUP BY CONVERT(date, o.Order_Time)
            ORDER BY CONVERT(date, o.Order_Time)
            """, nativeQuery = true)
    List<Object[]> getSoldQuantityPerDayNative();

    // Orders per day in a given month/year
    @Query(value = """
            SELECT CONVERT(date, o.Order_Time) AS day,
                   COUNT(*) AS orders
            FROM POSOder o
            WHERE YEAR(o.Order_Time) = :year AND MONTH(o.Order_Time) = :month
            GROUP BY CONVERT(date, o.Order_Time)
            ORDER BY CONVERT(date, o.Order_Time)
            """, nativeQuery = true)
    List<Object[]> getOrdersPerDayInMonth(@Param("year") int year, @Param("month") int month);

    // Total orders in month
    @Query(value = "SELECT COUNT(*) FROM POSOder WHERE YEAR(Order_Time) = :year AND MONTH(Order_Time) = :month", nativeQuery = true)
    Object countOrdersInMonthNative(@Param("year") int year, @Param("month") int month);

    // Orders by status
    @Query(value = """
            SELECT o.Status AS status, COUNT(*) AS cnt
            FROM POSOder o
            GROUP BY o.Status
            """, nativeQuery = true)
    List<Object[]> countOrdersGroupByStatusNative();

    // Orders by payment method (assumes PaymentMethod table has ID and Name)
    @Query(value = """
            SELECT pm.ID AS paymentMethodId,
                   pm.Name AS paymentMethodName,
                   COUNT(*) AS cnt
            FROM POSOder o
            JOIN PaymentMethod pm ON o.PaymentMethod = pm.ID
            GROUP BY pm.ID, pm.Name
            """, nativeQuery = true)
    List<Object[]> countOrdersGroupByPaymentMethodNative();

    // Low stock variations (<= threshold)
    @Query(value = """
            SELECT v.ID, v.Name, v.Quantity
            FROM Variation v
            WHERE v.Quantity <= :threshold
            ORDER BY v.Quantity ASC
            """, nativeQuery = true)
    List<Object[]> findLowStockVariationsNative(@Param("threshold") int threshold);

    // Revenue per day in a date range (start inclusive, end exclusive)
    @Query(value = """
            SELECT CONVERT(date, o.Order_Time) AS day,
                   SUM(o.Total_Amount) AS revenue
            FROM POSOder o
            WHERE o.Order_Time >= :start AND o.Order_Time < :end
            GROUP BY CONVERT(date, o.Order_Time)
            ORDER BY CONVERT(date, o.Order_Time)
            """, nativeQuery = true)
    List<Object[]> getRevenuePerDayRangeNative(@Param("start") String startIso, @Param("end") String endIso);

    @Query(value = "SELECT COUNT(*) FROM Variation WHERE Quantity <= :threshold", nativeQuery = true)
    Object countLowStockVariation(@Param("threshold") int threshold);

    // Count all variations (total products)
    @Query(value = "SELECT COUNT(*) FROM Variation", nativeQuery = true)
    Object countAllVariationsNative();

    @Query("SELECT SUM(v.Quantity) FROM Variation v")
    Long countQuantityVariation();

    // Lấy tổng doanh thu từ POSOrder (chỉ tính Status = 1 nếu là đơn hàng thành công)
    @Query("SELECT SUM(po.Total_Amount) FROM POSOrder po WHERE po.Status = 3")
    Double getTotalRevenue();

    // Tổng doanh thu trong tháng của các đơn đã thanh toán (Status = 3)
    @Query("""
                SELECT COALESCE(SUM(po.Total_Amount), 0)
                FROM POSOrder po
                WHERE YEAR(po.Payment_Time) = :year AND MONTH(po.Payment_Time) = :month AND po.Status = 3
            """)
    Double getPaidOrderRevenueInMonth(@Param("year") int year, @Param("month") int month);

    // Tổng doanh thu trong ngày của các đơn đã thanh toán (Status = 3)
    @Query("""
                SELECT COALESCE(SUM(po.Total_Amount), 0)
                FROM POSOrder po
                WHERE DATE(po.Payment_Time) = :date AND po.Status = 3
            """)
    Double getPaidOrderRevenueInDay(@Param("date") java.sql.Date date);

    // Đếm tổng các đơn hàng bị hủy trong tháng (Status = 2)
    @Query("""
                SELECT COUNT(po)
                FROM POSOrder po
                WHERE YEAR(po.Order_Time) = :year AND MONTH(po.Order_Time) = :month AND po.Status = 2
            """)
    Long countCanceledOrdersInMonth(@Param("year") int year, @Param("month") int month);
}
