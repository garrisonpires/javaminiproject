package com.virtuallab.dto;

public class MonthlyUsageDTO {
    private String month;
    private long usage;

    public MonthlyUsageDTO(String month, long usage) {
        this.month = month;
        this.usage = usage;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public long getUsage() {
        return usage;
    }

    public void setUsage(long usage) {
        this.usage = usage;
    }
}
