package com.sendsms.africastalking.controller;

import com.africastalking.SmsService;
import com.africastalking.sms.Subscription;
import com.sendsms.africastalking.model.AlertType;
import com.sendsms.africastalking.model.PojoSms;
import com.sendsms.africastalking.model.SmsOutbox;
import com.sendsms.africastalking.model.Subscriber;
import com.sendsms.africastalking.repository.SmsOutboxRepository;
import com.sendsms.africastalking.service.IvoryLottoService;
import com.sendsms.africastalking.service.PremiumService;
import com.sendsms.africastalking.service.SubscriberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Christian Amani on 18/04/2019.
 */
@RestController
@RequestMapping("/api/sms/")
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
public class RestSmsController {

    private final Logger LOG = LoggerFactory.getLogger(RestSmsController.class);

    private final SmsService smsService;
    private final SmsOutboxRepository smsOutboxRepository;
    private final PremiumService premiumService;
    private final SubscriberService subscriberService;
    private final IvoryLottoService ivoryLottoService;

    public RestSmsController(SmsService smsService, SmsOutboxRepository smsOutboxRepository
            , PremiumService premiumService, SubscriberService subscriberService, IvoryLottoService ivoryLottoService) {
        this.smsService = smsService;
        this.smsOutboxRepository = smsOutboxRepository;
        this.premiumService = premiumService;
        this.subscriberService = subscriberService;
        this.ivoryLottoService = ivoryLottoService;
    }

    @GetMapping("/outboxes")
    public ResponseEntity<List<SmsOutbox>> getSmsOutboxed() {
        LOG.debug("Process 'restSmsOutboxed' starting");
        List<SmsOutbox> smsOutboxes = smsOutboxRepository.findAll();
        if (smsOutboxes.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(smsOutboxes, HttpStatus.OK);
    }

    @GetMapping("/subscriptions/{shortcode}/{keyword}")
    public ResponseEntity<List<Subscription>> getSubscribers(@PathVariable("shortcode") String shortCode
            , @PathVariable("keyword") String keyword) {
        LOG.debug("Process 'getSubscribers' starting");
        List<Subscription> subscriptions = premiumService.fetchSubscribers(shortCode, keyword);
        if (subscriptions.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    @PostMapping("/bulk/send")
    public ResponseEntity<List<SmsOutbox>> postSendSms(@RequestBody PojoSms sms) {
        LOG.debug("Process 'postSendSms' starting");
        String number = sms.getNumber();
        String[] numbers = number.split(";");
        String message = sms.getMessage();
        if (numbers.length == 0)
            numbers = new String[]{number};
        List<SmsOutbox> smsOutboxes = premiumService.sendBulkSms(numbers, message);
        return new ResponseEntity<>(smsOutboxes, HttpStatus.OK);
    }
}
