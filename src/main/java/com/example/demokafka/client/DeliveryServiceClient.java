package com.example.demokafka.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "delivery-service", url = "${delivery.service.url}")
public interface DeliveryServiceClient {

    @PostMapping("/deliveries")
    DeliveryDto createDelivery(@RequestBody CreateDeliveryDto deliveryRequest);
}
