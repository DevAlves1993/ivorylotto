package com.sendsms.africastalking.repository;

import com.sendsms.africastalking.model.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Entity;
import java.util.Optional;

/**
 * Created by Christian Amani on 16/04/2019.
 */
public interface SubscriberRepository extends JpaRepository<Subscriber,Long> {

    @Query("SELECT sub.phoneNumber from Subscriber as sub where sub.id = :id")
    String findPhoneNumberById(@Param("id") long id);

    Optional<Subscriber> findByPhoneNumber(String phoneNumber);
}
