package com.sendsms.africastalking.model;

import java.util.Optional;

/**
 * @author Christian Amani on 17/04/2019.
 */
public class IncomingMessage {

    private String id;

    private String date;

    private String from;

    private Optional<String> linkId;

    private String text;

    private String to;

    private String networkCode;

    public IncomingMessage() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Optional<String> getLinkId() {
        return linkId;
    }

    public void setLinkId(Optional<String> linkId) {
        this.linkId = linkId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getNetworkCode() {
        return networkCode;
    }

    public void setNetworkCode(String networkCode) {
        this.networkCode = networkCode;
    }
}
