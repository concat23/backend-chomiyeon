package com.ecosystem.chomiyeon.entity;

import lombok.Getter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@Table(name = "cmy_polls")
public class CmyPoll extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 140)
    private String question;

    @OneToMany(
            mappedBy = "cmyPoll",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Size(min = 2, max = 6)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 30)
    private List<CmyChoice> cmyChoices = new ArrayList<>();

    @NotNull
    private Instant expirationDateTime;

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setCmyChoices(List<CmyChoice> cmyChoices) {
        this.cmyChoices = cmyChoices;
    }

    public void setExpirationDateTime(Instant expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }

    public void addCmyChoice(CmyChoice cmyChoice) {
        cmyChoices.add(cmyChoice);
        cmyChoice.setCmyPoll(this);
    }

    public void removeChoice(CmyChoice cmyChoice) {
        cmyChoices.remove(cmyChoice);
        cmyChoice.setCmyPoll(null);
    }
}
