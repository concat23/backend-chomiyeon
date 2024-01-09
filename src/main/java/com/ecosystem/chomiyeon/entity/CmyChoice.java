package com.ecosystem.chomiyeon.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "cmy_choices")
public class CmyChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cmy_poll_id", nullable = false)
    private CmyPoll cmyPoll;

    public CmyChoice() {

    }

    public CmyChoice(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CmyPoll getCmyPoll() {
        return cmyPoll;
    }

    public void setCmyPoll(CmyPoll cmyPoll) {
        this.cmyPoll = cmyPoll;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CmyChoice cmyChoice = (CmyChoice) o;
        return Objects.equals(id, cmyChoice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
