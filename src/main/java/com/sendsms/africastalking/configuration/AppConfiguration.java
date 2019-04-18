package com.sendsms.africastalking.configuration;

import com.africastalking.AfricasTalking;
import com.africastalking.Logger;
import com.africastalking.SmsService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Stream;

/**
 * Created by Christian Amani on 05/04/2019.
 */
@Configuration
public class AppConfiguration {

    private final org.slf4j.Logger LOG = LoggerFactory.getLogger(AppConfiguration.class);

    @Value("${africastalking.username}")
    private String userName;
    @Value("${africastalking.key}")
    private String apiKey;

    @Bean
    SmsService smsService() {
        AfricasTalking.initialize(userName, apiKey);
        AfricasTalking.setLogger(new Logger() {
            @Override
            public void log(String s, Object... objects) {
                LOG.info(s);
                Stream.of(objects)
                        .forEach(o -> LOG.info(o.toString()));
            }
        });
        return AfricasTalking.getService(AfricasTalking.SERVICE_SMS);
    }
}
