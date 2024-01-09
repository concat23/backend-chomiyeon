package com.ecosystem.chomiyeon.payload;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

public class CmyPollResponse {
    private Long id;
    private String question;
    private List<CmyChoiceResponse> choices;
    private CmyUserSummary createdBy;
    private Instant creationDateTime;
    private Instant expirationDateTime;
    private Boolean isExpired;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long selectedChoice;
    private Long totalVotes;

    public CmyPollResponse() {
    }

    public CmyPollResponse(Long id, String question, List<CmyChoiceResponse> choices, CmyUserSummary createdBy, Instant creationDateTime, Instant expirationDateTime, Boolean isExpired, Long selectedChoice, Long totalVotes) {
        this.id = id;
        this.question = question;
        this.choices = choices;
        this.createdBy = createdBy;
        this.creationDateTime = creationDateTime;
        this.expirationDateTime = expirationDateTime;
        this.isExpired = isExpired;
        this.selectedChoice = selectedChoice;
        this.totalVotes = totalVotes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<CmyChoiceResponse> getChoices() {
        return choices;
    }

    public void setChoices(List<CmyChoiceResponse> choices) {
        this.choices = choices;
    }

    public CmyUserSummary getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CmyUserSummary createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(Instant creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public Instant getExpirationDateTime() {
        return expirationDateTime;
    }

    public void setExpirationDateTime(Instant expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }

    public Boolean getExpired() {
        return isExpired;
    }

    public void setExpired(Boolean expired) {
        isExpired = expired;
    }

    public Long getSelectedChoice() {
        return selectedChoice;
    }

    public void setSelectedChoice(Long selectedChoice) {
        this.selectedChoice = selectedChoice;
    }

    public Long getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(Long totalVotes) {
        this.totalVotes = totalVotes;
    }
}
