package com.ecosystem.chomiyeon.payload;

public class AdminUserIdentityAvailability {
    private Boolean available;

    public AdminUserIdentityAvailability(Boolean available) {
        this.available = available;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}