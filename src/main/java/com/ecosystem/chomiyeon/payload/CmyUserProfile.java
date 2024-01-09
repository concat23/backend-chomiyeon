package com.ecosystem.chomiyeon.payload;

import java.time.Instant;

public class CmyUserProfile {
    private Long id;
    private String username;
    private String name;
    private Instant joinedAt;
    private Long cmyPollCount;
    private Long cmyVoteCount;

    public CmyUserProfile(Long id, String username, String name, Instant joinedAt, Long cmyPollCount, Long cmyVoteCount) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.joinedAt = joinedAt;
        this.cmyPollCount = cmyPollCount;
        this.cmyVoteCount = cmyVoteCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Instant joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Long getCmyPollCount() {
        return cmyPollCount;
    }

    public void setCmyPollCount(Long cmyPollCount) {
        this.cmyPollCount = cmyPollCount;
    }

    public Long getCmyVoteCount() {
        return cmyVoteCount;
    }

    public void setCmyVoteCount(Long cmyVoteCount) {
        this.cmyVoteCount = cmyVoteCount;
    }
}
