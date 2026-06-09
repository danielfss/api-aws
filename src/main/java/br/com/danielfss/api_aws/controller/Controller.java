package br.com.danielfss.api_aws.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.danielfss.api_aws.util.HealthDTO;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/")
public class Controller {
    
    @GetMapping("/health")
    public HealthDTO health() {
        return new HealthDTO("Up!");
    }

}
