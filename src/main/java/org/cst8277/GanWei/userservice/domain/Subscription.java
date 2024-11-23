package org.cst8277.GanWei.userservice.domain;

import java.time.Instant;

public class Subscription {

    private String producerId;
    private String subscriberId;
    private Instant subscribedAt;

    // Getters and Setters
    public String getProducerId() {
        return producerId;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }
}
