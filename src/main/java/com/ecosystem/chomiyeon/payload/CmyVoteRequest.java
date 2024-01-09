package com.ecosystem.chomiyeon.payload;

import javax.validation.constraints.NotNull;

public class CmyVoteRequest {
    @NotNull
    private Long cmyChoiceId;

    public Long getCmyChoiceId() {
        return cmyChoiceId;
    }

    public void setCmyChoiceId(Long cmyChoiceId) {
        this.cmyChoiceId = cmyChoiceId;
    }
}
