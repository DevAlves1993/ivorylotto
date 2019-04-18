package com.sendsms.africastalking.controller;

import com.africastalking.SmsService;
import com.africastalking.sms.Message;
import com.africastalking.sms.Recipient;
import com.africastalking.sms.Subscription;
import com.sendsms.africastalking.model.*;
import com.sendsms.africastalking.repository.SmsOutboxRepository;
import com.sendsms.africastalking.service.IvoryLottoService;
import com.sendsms.africastalking.service.PremiumService;
import com.sendsms.africastalking.service.SubscriberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Christian Amani on 05/04/2019.
 */
@Controller
@RequestMapping("/sms")
public class SmsController {

    private final Logger LOG = LoggerFactory.getLogger(SmsController.class);

    private final SmsService smsService;
    private final SmsOutboxRepository smsOutboxRepository;
    private final PremiumService premiumService;
    private final SubscriberService subscriberService;
    private final IvoryLottoService ivoryLottoService;

    @Autowired
    public SmsController(SmsService smsService, SmsOutboxRepository smsOutboxRepository, PremiumService premiumService
            , SubscriberService subscriberService, IvoryLottoService ivoryLottoService) {
        this.smsService = smsService;
        this.smsOutboxRepository = smsOutboxRepository;
        this.premiumService = premiumService;
        this.subscriberService = subscriberService;
        this.ivoryLottoService = ivoryLottoService;
    }

    @GetMapping("/bulk")
    public String pageBulk(Model model) {
        LOG.debug("Process 'pageBulk' starting");
        model.addAttribute("pojoSms", new PojoSms());
        List<SmsOutbox> smsOutboxes = smsOutboxRepository.findAll();
        model.addAttribute("smsOutboxes", smsOutboxes);
        return "sms_bulk";
    }

    @GetMapping("/premium/two/way")
    public String pageOnDemand(Model model) {
        LOG.debug("Process 'pageOnDemand' starting");
        model.addAttribute("pojoSms", new PojoSms());
        List<Subscription> subscribers = premiumService.fetchDefaultSubscribers();
        model.addAttribute("subscribers", subscribers);
        return "sms_two_way";
    }

    @GetMapping("/premium/on/subscription")
    public String pageOnSubscription(Model model) {
        LOG.debug("Process 'pageOnSubscription' starting");
        model.addAttribute("pojoSms", new PojoSms());
        List<Subscription> subscribers = premiumService.fetchDefaultSubscribers();
        model.addAttribute("subscribers", subscribers);
        return "sms_on_subscription";
    }

    @GetMapping("/subscriber/{subscriber}")
    public String pageSubscriber(Model model, @PathVariable("subscriber") long id) {
        LOG.info("Process 'pageSubscriber' starting");
        model.addAttribute("pojoSms", new PojoSms());
        Subscriber subscriber = subscriberService.getSubscriber(id);
        model.addAttribute("subscriber", subscriber);
        List<Message> messages = premiumService.fetchSubscriberMessages(id);
        model.addAttribute("messages", messages);
        return "sms_subscriber";
    }

    @PostMapping("/bulk/send")
    public String sendSms(@Valid PojoSms sms, RedirectAttributes model) {
        LOG.debug("Process 'sendSms' starting");
        String number = sms.getNumber();
        String[] numbers = number.split(";");
        String message = sms.getMessage();
        String arletMessage = "";
        AlertType type;
        if (numbers.length == 0)
            numbers = new String[]{number};
        List<SmsOutbox> smsOutboxes = premiumService.sendBulkSms(numbers, message);
        if (!smsOutboxes.isEmpty()) {
            arletMessage = "SMS sent";
            type = AlertType.success;
        } else {
            arletMessage = "Failure to send the SMS";
            type = AlertType.danger;
        }
        model.addFlashAttribute("alert_type", "alert-" + type.toString());
        model.addFlashAttribute("alert_message", arletMessage);
        return "redirect:/sms/bulk";
    }


    @PostMapping("/on/subscription/send")
    public String sendOnSubscription(@Valid PojoSms sms, RedirectAttributes model) {
        LOG.debug("Process 'sendOnSubscription' starting");
        List<Recipient> recipients = premiumService.sendOnSubscriptionSms(sms);
        if (recipients.isEmpty()) {
            model.addFlashAttribute("alert_type", "alert-" + AlertType.danger.toString());
            model.addFlashAttribute("alert_message", "Failure to send Premium SMS");
        } else {
            model.addFlashAttribute("alert_type", "alert-" + AlertType.success.toString());
            model.addFlashAttribute("alert_message", "Premium SMS sent");
        }
        return "redirect:/sms/premium/on/subscription";
    }


    @PostMapping("/callback/incoming/message")
    public ResponseEntity incomingCallbackMessage(@RequestBody IncomingMessage incomingMessage) {
        LOG.debug("Process");
        ivoryLottoService.receiveMessage(incomingMessage);
        return new ResponseEntity(HttpStatus.OK);
    }

}
