package com.sendsms.africastalking.service;

import com.africastalking.Callback;
import com.africastalking.SmsService;
import com.africastalking.sms.Message;
import com.africastalking.sms.Recipient;
import com.africastalking.sms.Subscription;
import com.sendsms.africastalking.model.PojoSms;
import com.sendsms.africastalking.model.SmsOutbox;
import com.sendsms.africastalking.model.Subscriber;
import com.sendsms.africastalking.repository.SmsOutboxRepository;
import com.sendsms.africastalking.repository.SubscriberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Christian Amani on 12/04/2019.
 */
@Service
public class PremiumService {

    private final Logger LOG = LoggerFactory.getLogger(PremiumService.class);

    @Value("${africastalking.premium.keyword}")
    private String keyword;
    @Value("${africastalking.premium.shortcode}")
    private String shortCode;
    @Value("${africastalking.senderid}")
    private String senderId;

    private final SmsService smsService;
    private final SubscriberRepository subscriberRepository;
    private final SmsOutboxRepository smsOutboxRepository;


    @Autowired
    public PremiumService(SmsService smsService, SubscriberRepository subscriberRepository
            , SmsOutboxRepository smsOutboxRepository) {
        this.smsService = smsService;
        this.subscriberRepository = subscriberRepository;
        this.smsOutboxRepository = smsOutboxRepository;
    }


    public List<Recipient> sendOnSubscriptionSms(PojoSms pojoSms) {
        LOG.debug("Process 'sendOnSubscriptionSms' starting");
        String[] recipients = new String[]{pojoSms.getNumber()};
        String message = pojoSms.getMessage();
        try {
            return smsService.sendPremium(message, senderId, keyword, "", recipients);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<SmsOutbox> sendBulkSms(String[] numbers, String message) {
        try {
            List<Recipient> recipients = smsService.send(message, "DemoSpring", numbers, false);
            return recipients.stream().
                    map(recipient -> {
                        SmsOutbox smsOutbox = SmsOutbox.fromRecipientAfricasTalking(recipient, "AFRICASTKNG", message);
                        smsOutboxRepository.save(smsOutbox);
                        return smsOutbox;
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public void sendPremiumSmS(String[] recipients, String message, Optional<String> linkOptional, Optional<Callback<List<Recipient>>> optionalCallback) {
        LOG.debug("Process 'sendPremiumSmS' starting");
        String linkId = linkOptional.orElse("");
        if (optionalCallback.isPresent())
            smsService.sendPremium(message, senderId, keyword, linkId, recipients, optionalCallback.get());
        else {
            try {
                smsService.sendPremium(message, senderId, keyword, linkId, recipients);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Subscription> fetchDefaultSubscribers() {
        LOG.debug("Process 'fetchDefaultSubscribers' starting");
        return fetchSubscribers(shortCode,keyword);
    }

    public List<Subscription> fetchSubscribers(String shortCode,String keyword) {
        LOG.debug("Process 'fetchSubscribers' starting");
        try {
            List<Subscription> subscriptions = smsService.fetchSubscriptions(shortCode, keyword);
            subscriptions
                    .stream()
                    .map(subscription -> {
                        LOG.info("Date subscribe : " + subscription.date);
                        LOG.info("Subscriber Phone Number : " + subscription.phoneNumber);
                        LOG.info("Subscriber Id : " + subscription.id);
                        return Subscriber.fromSubscription(subscription);
                    })
                    .parallel()
                    .forEach(subscriberRepository::save);
            return subscriptions;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<Message> fetchSubscriberMessages(long id) {
        LOG.debug("Process 'fetchSubscriberMessages' starting");
        String phoneNumber = subscriberRepository.findPhoneNumberById(id);
        return fetchMessages()
                .stream()
                .filter(message -> message.from.equalsIgnoreCase(phoneNumber))
                .collect(Collectors.toList());
    }

    public List<Message> fetchMessages() {
        LOG.debug("Process 'fetchMessages' starting");
        try {
            List<Message> messages = smsService.fetchMessages();
            messages.forEach(message -> {
                LOG.info("Date message : " + message.date);
                LOG.info(" : " + message.from);
                LOG.info(" : " + message.linkId);
                LOG.info(" : " + message.to);
                LOG.info(" : " + message.id);
                LOG.info(" : " + message.text);
            });
            return messages;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
