package com.sendsms.africastalking.model;

import com.africastalking.sms.Subscription;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Created by Christian Amani on 16/04/2019.
 */
@Table(name = "subscriber")
@Entity
public class Subscriber {

    @Id
    private long id;

    @Column(unique = true)
    private String phoneNumber;

    @Column(name = "subscribed_at")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime subscribedAt;

    public Subscriber() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getSubscribedAt() {
        return subscribedAt;
    }

    public void setSubscribedAt(LocalDateTime subscribedAt) {
        this.subscribedAt = subscribedAt;
    }

    public static Subscriber fromSubscription(Subscription subscription) {
        Subscriber subscriber = new Subscriber();
        subscriber.id = subscription.id;
        subscriber.phoneNumber = subscription.phoneNumber;
        subscriber.subscribedAt = LocalDateTime.parse(subscription.date);
        return subscriber;
    }
}
