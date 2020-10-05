package com.training.questionaire.qmanager.web.rest;


import com.training.questionaire.qmanager.web.dto.ExampleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qmanager")
public class HelloWorldRestController {

    @GetMapping("/helloWorld")
    public ResponseEntity<String> sayHelloWorld(){
        return ResponseEntity.ok().body("HELLO WORLD");
    }

    @GetMapping("/helloWorldComplex")
    public ResponseEntity<ExampleResponse> sayHelloWorldWithComplexity(){
        ExampleResponse response= new ExampleResponse();
        response.setMessageKey("qmanager.message.hello-world");
        response.setValue(200);
        response.setUser("someUser");
        return ResponseEntity.ok().body(response);
    }

}
