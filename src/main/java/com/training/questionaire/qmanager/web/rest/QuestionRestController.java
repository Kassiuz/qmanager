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
        Question repeatedQuestion = createQuestion(0L,"Some new question","some new Response",
                "new Level","new Seniortiy","new subject");
        List<Question> questionList = createQuestionList(7);
        questionList.add(repeatedQuestion);
        result.put("List",questionList);
        Set<Question> questionSet = createQuestionSet(7);
        questionSet.add(repeatedQuestion);
        result.put("Set",questionSet);
        return ResponseEntity.ok().body(result);
    }

    private List<Question> createQuestionList(long size){
        Collection<Question> questions = createQuestionCollection(size,false);
        return (List)questions;
    }

    private Set<Question> createQuestionSet(long size){
        Collection<Question> questions = createQuestionCollection(size,true);
        return (Set)questions;
    }

    private Collection<Question> createQuestionCollection(long size, boolean isSet){
        List<Question> questions = new ArrayList();
        for(long i=0; i<size;i++){
            questions.add(createQuestion(i,"Some question","some Response","Level",
                    "Seniortiy","subject"));
        }
        return isSet?new HashSet<>(questions):questions;
    }

    private Question createQuestion(Long id,String question, String response, String level, String seniority, String subject){
        Question newQuestion = new Question();
        newQuestion.setId(id);
        newQuestion.setQuestion(question);
        newQuestion.setResponse(response);
        newQuestion.setLevel(level);
        newQuestion.setSeniority(seniority);
        newQuestion.setSubject(subject);
        return newQuestion;
    }
}
