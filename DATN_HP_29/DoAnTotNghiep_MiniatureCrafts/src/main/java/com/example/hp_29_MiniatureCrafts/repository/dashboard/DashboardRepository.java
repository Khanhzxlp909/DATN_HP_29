package com.example.hp_29_MiniatureCrafts.repository.dashboard;

import com.example.hp_29_MiniatureCrafts.entity.POSOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DashboardRepository extends JpaRepository<POSOrder, Long> {

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

}
