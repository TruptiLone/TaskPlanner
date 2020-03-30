package com.trupti.eventplanner.eventplanner.Greetings;

public class GreetingBean {
    private String message;

    public GreetingBean(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "GreetingBean{" +
                "message='" + message + '\'' +
                '}';
    }
}
