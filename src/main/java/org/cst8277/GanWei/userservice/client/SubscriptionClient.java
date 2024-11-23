package org.cst8277.GanWei.userservice.client;

import java.util.List;
import org.cst8277.GanWei.userservice.domain.Subscription;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "message-service", url = "http://localhost:8082/api/subscriptions")
public interface SubscriptionClient {
    @GetMapping("/by-producer")
    List<Subscription> getSubscribersByProducerId(@RequestParam("producerId") String producerId);
}
