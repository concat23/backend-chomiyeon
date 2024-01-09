package com.ecosystem.chomiyeon.entity;

public class CmyChoiceVoteCount {
    private Long cmyChoiceId;
    private Long cmyVoteCount;

    public CmyChoiceVoteCount(Long cmyChoiceId, Long cmyVoteCount) {
        this.cmyChoiceId = cmyChoiceId;
        this.cmyVoteCount = cmyVoteCount;
    }

    public Long getCmyChoiceId() {
        return cmyChoiceId;
    }

    public void setCmyChoiceId(Long cmyChoiceId) {
        this.cmyChoiceId = cmyChoiceId;
    }

    public Long getCmyVoteCount() {
        return cmyVoteCount;
    }

    public void setCmyVoteCount(Long cmyVoteCount) {
        this.cmyVoteCount = cmyVoteCount;
    }
}
