package com.sendsms.africastalking.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * @author Christian Amani on 16/04/2019.
 */
@Table(name = "word_success")
@Entity
public class NumberSuccess {

    @Id
    private LocalDate created;

    private String word;

    public NumberSuccess() {
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
