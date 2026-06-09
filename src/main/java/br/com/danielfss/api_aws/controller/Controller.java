package br.com.danielfss.api_aws.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/")
public class Controller {
    
    @GetMapping("/health")
    public String health() {
        return "Up!";
    }

}
