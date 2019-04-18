package com.sendsms.africastalking.model;

import javax.validation.constraints.NotEmpty;

/**
 * @author Christian Amani on 05/04/2019.
 */
public class PojoSms {

    @NotEmpty
    private String number;

    @NotEmpty
    private String message;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
