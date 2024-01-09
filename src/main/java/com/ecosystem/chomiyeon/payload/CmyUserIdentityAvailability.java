package com.ecosystem.chomiyeon.payload;

public class CmyUserIdentityAvailability {
    private Boolean available;

    public CmyUserIdentityAvailability(Boolean available) {
        this.available = available;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
