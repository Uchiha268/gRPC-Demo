package com.interns.restDemo.restDemo;

import org.springframework.stereotype.Service;

@Service
public class HelloService {
    public HelloResponse sayHello(String name){
        String response ="Hello " + name + "!";
        HelloResponse helloResponse = new HelloResponse().builder().helloName(response).build();
        return helloResponse;
    }
}
