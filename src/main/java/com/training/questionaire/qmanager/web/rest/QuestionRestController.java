package com.training.questionaire.qmanager.web.rest;

import com.training.questionaire.qmanager.core.domain.entity.Question;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/qmanager")
public class QuestionRestController {

    @GetMapping("/testQuestions")
    public ResponseEntity<Map<String,Object>> sayHelloWorld(){
        Map<String,Object> result = new HashMap<>();
        List<Question> questionList = new ArrayList<>();
        Question repeatedQuestion = new Question();
        repeatedQuestion.setId(0L);
        repeatedQuestion.setQuestion("Some new question");
        repeatedQuestion.setResponse("some new Response");
        repeatedQuestion.setLevel("new Level");
        repeatedQuestion.setSeniority("new Seniortiy");
        repeatedQuestion.setSubject("new subject");
        questionList.add(repeatedQuestion);
        for(long i=0; i<7;i++){
            Question newQuestion = new Question();
            newQuestion.setId(i);
            newQuestion.setQuestion("Some question");
            newQuestion.setResponse("some Response");
            newQuestion.setLevel("Level");
            newQuestion.setSeniority("Seniortiy");
            newQuestion.setSubject("subject");
            questionList.add(newQuestion);
        }
        result.put("List",questionList);
        Set<Question> questionSet = new HashSet<>();
        questionSet.add(repeatedQuestion);
        for(long i=0; i<7;i++){
            Question newQuestion = new Question();
            newQuestion.setId(i);
            newQuestion.setQuestion("Some question");
            newQuestion.setResponse("some Response");
            newQuestion.setLevel("Level");
            newQuestion.setSeniority("Seniortiy");
            newQuestion.setSubject("subject");
            questionSet.add(newQuestion);
        }
        result.put("Set",questionSet);
        return ResponseEntity.ok().body(result);
    }
}
