package com.example.hp_29_MiniatureCrafts.service.dashboard;

import com.example.hp_29_MiniatureCrafts.dto.*;
import com.example.hp_29_MiniatureCrafts.dto.dashboard.*;
import com.example.hp_29_MiniatureCrafts.repository.dashboard.DashboardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DashboardService {

    private final DashboardRepository repo;

    public DashboardService(DashboardRepository repo) {
        this.repo = repo;
    }

    /* ------------------ Top products ------------------ */
    public List<TopProductDTO> getTopProducts(int limit) {
        List<Object[]> rows = repo.findTopSellingProductsNative(limit);
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
        Object o = repo.getTotalQuantitySoldNative();
        return toLong(o);
    }

    /* ------------------ Sold per day ------------------ */
    public List<SoldPerDayDTO> getSoldPerDay() {
        List<Object[]> rows = repo.getSoldQuantityPerDayNative();
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
        List<Object[]> rows = repo.getOrdersPerDayInMonth(year, month);
        // map day + orders into SoldPerDayDTO (quantity holds orders)
        return rows.stream().map(r -> new SoldPerDayDTO(toLocalDate(r[0]), ((Number) r[1]).longValue())).toList();
    }

    public Long countOrdersInMonth(int year, int month) {
        Object o = repo.countOrdersInMonthNative(year, month);
        return toLong(o);
    }

    /* ------------------ Orders by status ------------------ */
    public List<OrdersCountByStatusDTO> getOrdersCountByStatus() {
        List<Object[]> rows = repo.countOrdersGroupByStatusNative();
        return rows.stream().map(r -> {
            Integer status = r[0] == null ? null : ((Number) r[0]).intValue();
            Long count = r[1] == null ? 0L : ((Number) r[1]).longValue();
            return new OrdersCountByStatusDTO(status, count);
        }).toList();
    }

    /* ------------------ Orders by payment ------------------ */
    public List<OrdersCountByPaymentDTO> getOrdersCountByPayment() {
        List<Object[]> rows = repo.countOrdersGroupByPaymentMethodNative();
        return rows.stream().map(r -> {
            Long pid = r[0] == null ? null : ((Number) r[0]).longValue();
            String name = r[1] == null ? null : r[1].toString();
            Long count = r[2] == null ? 0L : ((Number) r[2]).longValue();
            return new OrdersCountByPaymentDTO(pid, name, count);
        }).toList();
    }

    /* ------------------ Low stock products ------------------ */
    public List<LowStockProductDTO> getLowStockProducts(int threshold) {
        List<Object[]> rows = repo.findLowStockVariationsNative(threshold);
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
        List<Object[]> rows = repo.getRevenuePerDayRangeNative(startIso, endIso);
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
        Object o = repo.countAllVariationsNative();
        return toLong(o);
    }

    /* ------------------ Low stock variations count ------------------ */
    public Long countLowStockVariation(int threshold) {
        Object o = repo.countLowStockVariation(threshold);
        return toLong(o);
    }

     /* ------------------ Total variations count ------------------ */
    public Long countQuantityVariation() {
        return repo.countQuantityVariation();
    }

    /* ------------------ Helpers ------------------ */
    private Long toLong(Object o) {
        if (o == null) return 0L;
        if (o instanceof Number) return ((Number) o).longValue();
        try { return Long.parseLong(o.toString()); } catch (Exception e) { return 0L; }
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
        if (s.length() >= 10) return LocalDate.parse(s.substring(0,10));
        return null;
    }
}
