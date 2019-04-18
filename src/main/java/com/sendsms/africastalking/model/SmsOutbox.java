package com.sendsms.africastalking.model;

import com.africastalking.sms.Recipient;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Christian Amani on 06/04/2019.
 */
@Table(name = "sms_outbox")
@Entity
public class SmsOutbox {

    @Id
    private String id;

    @Column(length = 100)
    private String message;

    @Column(name = "sender")
    private String from;

    @Column(name = "recipient")
    private String to;

    @Column()
    private String coast;

    @Column()
    @Enumerated(EnumType.STRING)
    private Status status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column()
    private LocalDateTime created;

    public SmsOutbox() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCoast() {
        return coast;
    }

    public void setCoast(String coast) {
        this.coast = coast;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public enum Status {
        success,
        failed;

        static Status fromString(String status) {
            if (status.equalsIgnoreCase("success"))
                return success;
            else
                return failed;
        }
        }

    public static SmsOutbox fromRecipientAfricasTalking(Recipient recipient,String from,String message) {
        SmsOutbox smsOutbox = new SmsOutbox();
        smsOutbox.from = from;
        smsOutbox.id = recipient.messageId;
        smsOutbox.to = recipient.number;
        smsOutbox.coast = recipient.cost;
        smsOutbox.message = message;
        smsOutbox.status = Status.fromString(recipient.status);
        smsOutbox.created = LocalDateTime.now();
        return smsOutbox;
    }
}
