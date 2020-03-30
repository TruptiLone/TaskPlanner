package com.trupti.eventplanner.eventplanner.Greetings;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping(path = "/greeting")
    public GreetingBean greeting(){
        return new GreetingBean("Hello World");
    }
}
