package com.example.hp_29_MiniatureCrafts.dto.dashboard;

public class MonthlyRevenueDTO {
    private int month;
    private Double totalRevenue;

    public MonthlyRevenueDTO(int month, Double totalRevenue) {
        this.month = month;
        this.totalRevenue = totalRevenue;
    }

    public MonthlyRevenueDTO() {
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
