package com.sendsms.africastalking.service;

import com.sendsms.africastalking.model.Subscriber;
import com.sendsms.africastalking.repository.SubscriberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Christian Amani on 16/04/2019.
 */
@Service
public class SubscriberService {

    private final Logger LOG = LoggerFactory.getLogger(SubscriberService.class);

    private final SubscriberRepository repository;

    @Autowired
    public SubscriberService(SubscriberRepository repository) {
        this.repository = repository;
    }

    public Subscriber getSubscriber(long id) {
        LOG.debug("Process 'getSubscriber' starting");
        return repository.findById(id).orElse(new Subscriber());
    }

    public Optional<Subscriber> getSubscriber(String phoneNumber) {
        LOG.debug("Process 'getSubscriber' starting");
        return repository.findByPhoneNumber(phoneNumber);
    }
}
