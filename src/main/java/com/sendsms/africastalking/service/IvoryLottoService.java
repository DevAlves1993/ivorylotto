package com.sendsms.africastalking.service;

import com.africastalking.Callback;
import com.africastalking.sms.Recipient;
import com.africastalking.sms.Subscription;
import com.sendsms.africastalking.model.IncomingMessage;
import com.sendsms.africastalking.model.NumberSuccess;
import com.sendsms.africastalking.model.Subscriber;
import com.sendsms.africastalking.repository.WordSuccessRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * @author Christian Amani on 16/04/2019.
 */
@Service
public class IvoryLottoService {

    private static String wordOfDay;

    private final Logger LOG = LoggerFactory.getLogger(IvoryLottoService.class);
    private final String LINK_ID = "NumberSuccess";
    private final String SUCCESS = "SUCCESS";

    private final PremiumService premiumService;
    private final WordSuccessRepository wordSuccessRepository;
    private final SubscriberService subscriberService;


    @Autowired
    public IvoryLottoService(PremiumService premiumService, WordSuccessRepository wordSuccessRepository
            , SubscriberService subscriberService) {
        this.premiumService = premiumService;
        this.wordSuccessRepository = wordSuccessRepository;
        this.subscriberService = subscriberService;
    }

    //@Scheduled(cron = "0 45 12 1/1 * ?")
    public void sendSuccessWord() {
        LOG.debug("Process 'sendSuccessWord' starting");
        successNumberGeneration();
        List<Subscription> subscriptions = premiumService.fetchDefaultSubscribers();
        String[] recipients = (String[]) subscriptions.stream()
                .map(subscription -> subscription.phoneNumber)
                .toArray();
        premiumService.sendPremiumSmS(recipients, wordOfDay, Optional.of("NumberSuccess"), Optional.of(new Callback<List<Recipient>>() {
            @Override
            public void onSuccess(List<Recipient> recipients) {
                // TODO : Notify
            }

            @Override
            public void onFailure(Throwable throwable) {
                // TODO : Catch
            }
        }));
    }

    public void receiveMessage(IncomingMessage message) {
        LOG.debug("Process 'receiveMessage' starting");
        String linkId = message.getLinkId().orElse("");
        if (linkId.equalsIgnoreCase(LINK_ID)) {
            Optional<NumberSuccess> optionalNumberSuccess = wordSuccessRepository.findById(LocalDate.now());
            optionalNumberSuccess.ifPresent(numberSuccess -> {
                String text = message.getText();
                String number = numberSuccess.getWord();
                if (text.equalsIgnoreCase(number)) {
                    String phoneNumber = message.getFrom();
                    Optional<Subscriber> optionalSubscriber = subscriberService.getSubscriber(phoneNumber);
                    optionalSubscriber.ifPresent((subscriber -> {
                        String recipient = subscriber.getPhoneNumber();
                        premiumService.sendPremiumSmS(new String[]{recipient}, SUCCESS, Optional.empty(), Optional.of(new Callback<List<Recipient>>() {
                            @Override
                            public void onSuccess(List<Recipient> recipients) {
                                // TODO : Notify
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                // TODO : Catch
                            }
                        }));
                    }));
                }
            });
        }
    }

    private void successNumberGeneration() {
        LOG.debug("Process 'wordSuccessGeneration' starting");
        Random random = new Random(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        long l = random.nextLong();
        wordOfDay = String.valueOf(l)
                .substring(1, 4);
        NumberSuccess numberSuccess = new NumberSuccess();
        numberSuccess.setCreated(LocalDate.now());
        numberSuccess.setWord(wordOfDay);
        wordSuccessRepository.save(numberSuccess);
    }
}
