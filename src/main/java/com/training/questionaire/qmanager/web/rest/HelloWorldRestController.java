package com.training.questionaire.qmanager.web.rest;


import com.training.questionaire.qmanager.web.dto.ExampleResponse;
import com.training.questionaire.qmanager.web.dto.TellMeHelloRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value="/tellMeHello")
    public ResponseEntity<ExampleResponse> tellMeHelloHandlerJSONtoXML(@RequestBody TellMeHelloRequest request){
        ExampleResponse response= new ExampleResponse();
        response.setMessageKey("qmanager.message.hello-world."+request.getUsername()+"."+request.getAge());
        response.setValue(200);
        response.setUser(request.getUsername());
        return ResponseEntity.ok().body(response);
    }


}
