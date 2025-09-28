package br.com.app.controllers;

import br.com.app.config.GreetingConfiguration;
import br.com.app.models.Greeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "%s, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    @Autowired
    private GreetingConfiguration configuration;

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(defaultValue = "")
            String name){
        return  new Greeting(counter.incrementAndGet(), String.format(template, configuration.getDefaultValue(),  name));
    }
}
