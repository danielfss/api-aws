package br.com.danielfss.api_aws.sqs.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.danielfss.api_aws.sqs.service.SqsService;

@RestController
@RequestMapping("/sqs")
public class SqsController {

    private final SqsService service;

    public SqsController(SqsService service) {
        this.service = service;
    }

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestParam String message) {
        return ResponseEntity.ok(service.sendMessage(message));
    }

    @GetMapping("/process")
    public ResponseEntity<List<Map<String, String>>> process() {
        return ResponseEntity.ok(service.receiveAndProcess());
    }

    @GetMapping("/peek")
    public ResponseEntity<List<String>> peek() {
        return ResponseEntity.ok(service.peekMessages());
    }
    
}