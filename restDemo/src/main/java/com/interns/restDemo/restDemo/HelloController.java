package com.interns.restDemo.restDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final HelloService helloService;

    @Autowired
    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @PostMapping("/")
   public ResponseEntity<HelloResponse> sayHello(@RequestBody HelloRequest helloRequest){
       HelloResponse helloResponse = helloService.sayHello(helloRequest.getName());
       return new ResponseEntity<>(helloResponse, HttpStatus.ACCEPTED);
    }
}
