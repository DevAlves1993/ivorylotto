package com.sendsms.africastalking.repository;

import com.sendsms.africastalking.model.NumberSuccess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

/**
 * @author Christian Amani on 16/04/2019.
 */
public interface WordSuccessRepository extends JpaRepository<NumberSuccess, LocalDate> {
}
