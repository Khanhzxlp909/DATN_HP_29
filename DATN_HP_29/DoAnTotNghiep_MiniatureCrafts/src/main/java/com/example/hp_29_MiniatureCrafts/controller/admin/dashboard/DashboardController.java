package com.example.hp_29_MiniatureCrafts.controller.admin.dashboard;

import com.example.hp_29_MiniatureCrafts.dto.*;
import com.example.hp_29_MiniatureCrafts.dto.dashboard.*;
import com.example.hp_29_MiniatureCrafts.service.dashboard.DashboardService;
import com.example.hp_29_MiniatureCrafts.service.product.VariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService service;

    @Autowired
    VariationService variationService;

    @GetMapping("/monthly-revenue")
    public ResponseEntity<Map<String, List<Double>>> getMonthlyRevenue(
            @RequestParam(required = false) Integer year) {

        int targetYear = (year != null) ? year : LocalDate.now().getYear();
        return ResponseEntity.ok(service.getRevenueByHalfYear(targetYear));
    }

    @GetMapping("/day-revenue")
    public ResponseEntity<Map<String, List<Double>>> getDayRevenue(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month

    ) {

        int targetYear = (year != null) ? year : LocalDate.now().getYear();
        int targetMonth = (month != null) ? month : LocalDate.now().getMonthValue();
        return ResponseEntity.ok(service.getRevenueByHalfMonth(targetYear, targetMonth));
    }

    // API: Tổng số lượng sản phẩm
    @GetMapping("/total-quantity")
    public Long getTotalProductQuantity() {
        return service.getTotalProductQuantity();
    }

    // API: Tổng doanh thu
    @GetMapping("/total-revenue")
    public Double getTotalRevenue() {
        return service.getTotalRevenue();
    }

    @GetMapping("/top-products")
    public ResponseEntity<List<TopProductDTO>> topProducts(@RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(service.getTopProducts(limit));
    }

    @GetMapping("/variation/bestseller")
    public Page<VariationDTO> bestSeller(Pageable pageable) {
        return variationService.getVariationsByBestseller(pageable);
    }

    @GetMapping("/total-quantity-sold")
    public ResponseEntity<SimpleCountDTO> totalQuantitySold() {
        return ResponseEntity.ok(new SimpleCountDTO(service.getTotalQuantitySold()));
    }

    @GetMapping("/sold-per-day")
    public ResponseEntity<List<SoldPerDayDTO>> soldPerDay() {
        return ResponseEntity.ok(service.getSoldPerDay());
    }

    @GetMapping("/orders-per-day")
    public ResponseEntity<List<SoldPerDayDTO>> ordersPerDayInMonth(
            @RequestParam int year,
            @RequestParam int month) {
        return ResponseEntity.ok(service.getOrdersPerDayInMonth(year, month));
    }

    @GetMapping("/orders-in-month/count")
    public ResponseEntity<SimpleCountDTO> ordersInMonthCount(@RequestParam int year, @RequestParam int month) {
        return ResponseEntity.ok(new SimpleCountDTO(service.countOrdersInMonth(year, month)));
    }

    @GetMapping("/orders-by-status")
    public ResponseEntity<List<OrdersCountByStatusDTO>> ordersByStatus() {
        return ResponseEntity.ok(service.getOrdersCountByStatus());
    }

    @GetMapping("/orders-by-payment")
    public ResponseEntity<List<OrdersCountByPaymentDTO>> ordersByPayment() {
        return ResponseEntity.ok(service.getOrdersCountByPayment());
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<LowStockProductDTO>> lowStock(@RequestParam(defaultValue = "5") int threshold) {
        return ResponseEntity.ok(service.getLowStockProducts(threshold));
    }

    @GetMapping("/revenue-range")
    public ResponseEntity<List<RevenuePointDTO>> revenuePerDayRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        // end is exclusive (client can pass end = next day to include end)
        return ResponseEntity.ok(service.getRevenuePerDayRange(start, end));
    }

    @GetMapping("/products/count")
    public ResponseEntity<SimpleCountDTO> countProducts() {
        return ResponseEntity.ok(new SimpleCountDTO(service.countAllVariations()));
    }

    @GetMapping("/low-stock/count")
    public ResponseEntity<SimpleCountDTO> countQuantityLowStock() {
        return ResponseEntity.ok(new SimpleCountDTO(service.countAllVariations()));
    }

    @GetMapping("/products/count/quantity")
    public ResponseEntity<SimpleCountDTO> countQuantityVariations() {
        return ResponseEntity.ok(new SimpleCountDTO(service.countQuantityVariation()));
    }

    // Tổng doanh thu trong tháng của các đơn đã thanh toán
    @GetMapping("/paid-revenue/month")
    public ResponseEntity<Double> getPaidOrderRevenueInMonth(
            @RequestParam int year,
            @RequestParam int month) {
        return ResponseEntity.ok(service.getPaidOrderRevenueInMonth(year, month));
    }

    // Tổng doanh thu trong ngày của các đơn đã thanh toán
    @GetMapping("/paid-revenue/day")
    public ResponseEntity<Double> getPaidOrderRevenueInDay(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(service.getPaidOrderRevenueInDay(date));
    }

    // Đếm tổng các đơn hàng bị hủy trong tháng
    @GetMapping("/orders-in-month/canceled")
    public ResponseEntity<Long> countCanceledOrdersInMonth(
            @RequestParam int year,
            @RequestParam int month) {
        return ResponseEntity.ok(service.countCanceledOrdersInMonth(year, month));
    }

    // Tổng doanh thu trong tháng hiện tại của các đơn đã thanh toán
    @GetMapping("/paid-revenue/current-month")
    public ResponseEntity<Double> getPaidOrderRevenueCurrentMonth() {
        return ResponseEntity.ok(service.getPaidOrderRevenueCurrentMonth());
    }

}
