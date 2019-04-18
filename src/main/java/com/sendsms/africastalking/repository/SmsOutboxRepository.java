package com.sendsms.africastalking.repository;

import com.sendsms.africastalking.model.SmsOutbox;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Christian Amani on 06/04/2019.
 */
public interface SmsOutboxRepository extends JpaRepository<SmsOutbox,String> {
}
