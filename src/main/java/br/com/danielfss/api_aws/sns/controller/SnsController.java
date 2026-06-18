package br.com.danielfss.api_aws.sns.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.danielfss.api_aws.sns.service.SnsService;

@RestController
@RequestMapping("/sns")
public class SnsController {

    private final SnsService service;

    public SnsController(SnsService service) {
        this.service = service;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestParam String message) {
        return ResponseEntity.ok(service.publish(message));
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestParam String email) {
        return ResponseEntity.ok(service.subscribeEmail(email));
    }

    @PostMapping("/subscribe-app")
    public ResponseEntity<String> subscribeApp(@RequestParam String endpointUrl) {
        return ResponseEntity.ok(service.subscribeHttp(endpointUrl));
    }

    @PostMapping("/sms")
    public ResponseEntity<String> sms(
            @RequestParam String phoneNumber,
            @RequestParam String message) {
        return ResponseEntity.ok(service.sendSms(phoneNumber, message));
    }
}