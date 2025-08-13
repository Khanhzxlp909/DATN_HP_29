package com.example.hp_29_MiniatureCrafts.service.dashboard;

import com.example.hp_29_MiniatureCrafts.dto.dashboard.*;
import com.example.hp_29_MiniatureCrafts.repository.dashboard.DashboardRepository;
import com.example.hp_29_MiniatureCrafts.repository.product.VariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private VariationRepository variationRepo;

    /* ------------------ Revenue Monthly ------------------ */

    public Map<String, List<Double>> getRevenueByHalfYear(int year) {
        List<Object[]> results = dashboardRepository.getMonthlyRevenue(year);

        // Khởi tạo list 12 tháng = 0 doanh thu
        List<Double> monthlyRevenue = new ArrayList<>(Collections.nCopies(12, 0.0));

        // Gán dữ liệu từ query
        for (Object[] row : results) {
            int month = ((Number) row[0]).intValue(); // tháng (1-12)
            double revenue = ((Number) row[1]).doubleValue();
            monthlyRevenue.set(month - 1, revenue);
        }

        // Chia thành 2 list
        List<Double> firstHalf = monthlyRevenue.subList(0, 6);  // Tháng 1-6
        List<Double> secondHalf = monthlyRevenue.subList(6, 12); // Tháng 7-12

        Map<String, List<Double>> response = new HashMap<>();
        response.put("firstHalf", new ArrayList<>(firstHalf));
        response.put("secondHalf", new ArrayList<>(secondHalf));

        return response;
    }

    /* ------------------ Revenue Day ------------------ */

    public Map<String, List<Double>> getRevenueByHalfMonth(int year, int month) {
        List<Object[]> results = dashboardRepository.getDailyRevenueInMonth(year, month);

        // Số ngày tối đa trong tháng
        int daysInMonth = YearMonth.of(year, month).lengthOfMonth();

        // Khởi tạo list ngày = 0 doanh thu
        List<Double> dayRevenue = new ArrayList<>(Collections.nCopies(daysInMonth, 0.0));

        // Gán dữ liệu từ query
        for (Object[] row : results) {
            LocalDate date = ((Date) row[0]).toLocalDate(); // chuyển từ java.sql.Date sang LocalDate
            double revenue = ((Number) row[1]).doubleValue();
            int dayOfMonth = date.getDayOfMonth();
            dayRevenue.set(dayOfMonth - 1, revenue);
        }

        // Chia thành 2 list
        List<Double> firstHalf = dayRevenue.subList(0, Math.min(15, daysInMonth));
        List<Double> secondHalf = (daysInMonth > 15) ? dayRevenue.subList(15, daysInMonth) : Collections.emptyList();

        Map<String, List<Double>> response = new HashMap<>();
        response.put("firstHalf", new ArrayList<>(firstHalf));
        response.put("secondHalf", new ArrayList<>(secondHalf));

        return response;
    }

    /* ------------------ Product Quantity ------------------ */

    public Long getTotalProductQuantity() {
        return variationRepo.getTotalProductQuantity();
    }
    /* ------------------ Total Revennue ------------------ */
    public Double getTotalRevenue() {
        return dashboardRepository.getTotalRevenue();
    }

    /* ------------------ Top products ------------------ */
    public List<TopProductDTO> getTopProducts(int limit) {
        List<Object[]> rows = dashboardRepository.findTopSellingProductsNative(limit);
        return mapTopProducts(rows);
    }

    private List<TopProductDTO> mapTopProducts(List<Object[]> rows) {
        return rows.stream().map(r -> {
            Long vid = r[0] == null ? null : ((Number) r[0]).longValue();
            String name = r[1] == null ? null : r[1].toString();
            Long totalSold = r[2] == null ? 0L : ((Number) r[2]).longValue();
            BigDecimal totalRev = r[3] == null ? BigDecimal.ZERO : new BigDecimal(r[3].toString());
            return new TopProductDTO(vid, name, totalSold, totalRev);
        }).toList();
    }

    /* ------------------ Total quantity sold ------------------ */
    public Long getTotalQuantitySold() {
        Object o = dashboardRepository.getTotalQuantitySoldNative();
        return toLong(o);
    }

    /* ------------------ Sold per day ------------------ */
    public List<SoldPerDayDTO> getSoldPerDay() {
        List<Object[]> rows = dashboardRepository.getSoldQuantityPerDayNative();
        return mapSoldPerDay(rows);
    }

    private List<SoldPerDayDTO> mapSoldPerDay(List<Object[]> rows) {
        return rows.stream().map(r -> {
            LocalDate day = toLocalDate(r[0]);
            Long qty = r[1] == null ? 0L : ((Number) r[1]).longValue();
            return new SoldPerDayDTO(day, qty);
        }).toList();
    }

    /* ------------------ Orders per day in month & total in month ------------------ */
    public List<SoldPerDayDTO> getOrdersPerDayInMonth(int year, int month) {
        List<Object[]> rows = dashboardRepository.getOrdersPerDayInMonth(year, month);
        // map day + orders into SoldPerDayDTO (quantity holds orders)
        return rows.stream().map(r -> new SoldPerDayDTO(toLocalDate(r[0]), ((Number) r[1]).longValue())).toList();
    }

    public Long countOrdersInMonth(int year, int month) {
        Object o = dashboardRepository.countOrdersInMonthNative(year, month);
        return toLong(o);
    }

    /* ------------------ Orders by status ------------------ */
    public List<OrdersCountByStatusDTO> getOrdersCountByStatus() {
        List<Object[]> rows = dashboardRepository.countOrdersGroupByStatusNative();
        return rows.stream().map(r -> {
            Integer status = r[0] == null ? null : ((Number) r[0]).intValue();
            Long count = r[1] == null ? 0L : ((Number) r[1]).longValue();
            return new OrdersCountByStatusDTO(status, count);
        }).toList();
    }

    /* ------------------ Orders by payment ------------------ */
    public List<OrdersCountByPaymentDTO> getOrdersCountByPayment() {
        List<Object[]> rows = dashboardRepository.countOrdersGroupByPaymentMethodNative();
        return rows.stream().map(r -> {
            Long pid = r[0] == null ? null : ((Number) r[0]).longValue();
            String name = r[1] == null ? null : r[1].toString();
            Long count = r[2] == null ? 0L : ((Number) r[2]).longValue();
            return new OrdersCountByPaymentDTO(pid, name, count);
        }).toList();
    }

    /* ------------------ Low stock products ------------------ */
    public List<LowStockProductDTO> getLowStockProducts(int threshold) {
        List<Object[]> rows = dashboardRepository.findLowStockVariationsNative(threshold);
        return rows.stream().map(r -> {
            Long vid = r[0] == null ? null : ((Number) r[0]).longValue();
            String name = r[1] == null ? null : r[1].toString();
            Integer qty = r[2] == null ? 0 : ((Number) r[2]).intValue();
            return new LowStockProductDTO(vid, name, qty);
        }).toList();
    }

    /* ------------------ Revenue per day range ------------------ */
    public List<RevenuePointDTO> getRevenuePerDayRange(LocalDate startInclusive, LocalDate endExclusive) {
        // repo expects ISO strings (SQL Server handles implicit conversion)
        String startIso = startInclusive.toString(); // 'yyyy-MM-dd'
        String endIso = endExclusive.toString();
        List<Object[]> rows = dashboardRepository.getRevenuePerDayRangeNative(startIso, endIso);
        return mapRevenuePerDay(rows);
    }

    private List<RevenuePointDTO> mapRevenuePerDay(List<Object[]> rows) {
        return rows.stream().map(r -> {
            LocalDate day = toLocalDate(r[0]);
            BigDecimal revenue = r[1] == null ? BigDecimal.ZERO : new BigDecimal(r[1].toString());
            return new RevenuePointDTO(day, revenue);
        }).toList();
    }

    /* ------------------ Total variations count ------------------ */
    public Long countAllVariations() {
        Object o = dashboardRepository.countAllVariationsNative();
        return toLong(o);
    }

    /* ------------------ Low stock variations count ------------------ */
    public Long countLowStockVariation(int threshold) {
        Object o = dashboardRepository.countLowStockVariation(threshold);
        return toLong(o);
    }

    /* ------------------ Total variations count ------------------ */
    public Long countQuantityVariation() {
        return dashboardRepository.countQuantityVariation();
    }

    // Tổng doanh thu trong tháng của các đơn đã thanh toán
    public Double getPaidOrderRevenueInMonth(int year, int month) {
        return dashboardRepository.getPaidOrderRevenueInMonth(year, month);
    }

    // Tổng doanh thu trong ngày của các đơn đã thanh toán
    public Double getPaidOrderRevenueInDay(LocalDate date) {
        return dashboardRepository.getPaidOrderRevenueInDay(java.sql.Date.valueOf(date));
    }

    // Đếm tổng các đơn hàng bị hủy trong tháng
    public Long countCanceledOrdersInMonth(int year, int month) {
        return dashboardRepository.countCanceledOrdersInMonth(year, month);
    }

    // Doanh thu tháng hiện tại của các đơn đã thanh toán
    public Double getPaidOrderRevenueCurrentMonth() {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        return dashboardRepository.getPaidOrderRevenueInMonth(year, month);
    }

    /* ------------------ Helpers ------------------ */
    private Long toLong(Object o) {
        if (o == null) return 0L;
        if (o instanceof Number) return ((Number) o).longValue();
        try {
            return Long.parseLong(o.toString());
        } catch (Exception e) {
            return 0L;
        }
    }

    private LocalDate toLocalDate(Object o) {
        if (o == null) return null;
        if (o instanceof Date) {
            return ((Date) o).toLocalDate();
        }
        if (o instanceof Timestamp) {
            return ((Timestamp) o).toLocalDateTime().toLocalDate();
        }
        // fallback: parse string yyyy-MM-dd or yyyy-MM-dd HH:mm:ss
        String s = o.toString();
        if (s.length() >= 10) return LocalDate.parse(s.substring(0, 10));
        return null;
    }
}
