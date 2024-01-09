package com.ecosystem.chomiyeon.payload;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class CmyPollRequest {
    @NotBlank
    @Size(max = 140)
    private String question;

    @NotNull
    @Size(min = 2, max = 6)
    @Valid
    private List<CmyChoiceRequest> choices;

    @NotNull
    @Valid
    private CmyPollLength cmyPollLength;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<CmyChoiceRequest> getChoices() {
        return choices;
    }

    public void setChoices(List<CmyChoiceRequest> choices) {
        this.choices = choices;
    }

    public CmyPollLength getPollLength() {
        return cmyPollLength;
    }

    public void setPollLength(CmyPollLength pollLength) {
        this.cmyPollLength = pollLength;
    }
}
