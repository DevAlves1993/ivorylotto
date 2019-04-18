package com.sendsms.africastalking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AfricastalkingApplication {

    public static void main(String[] args) {
        SpringApplication.run(AfricastalkingApplication.class, args);
    }
}
