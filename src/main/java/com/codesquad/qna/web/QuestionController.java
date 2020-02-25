package com.codesquad.qna.web;

import com.codesquad.qna.domain.Question;
import com.codesquad.qna.domain.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class QuestionController {

    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/questions")
    public String create(Question newQuestion) {
        newQuestion.setCreatedDateTime(LocalDateTime.now());
        questionRepository.save(newQuestion);
        log.info("create : {}", newQuestion);
        return "redirect:/";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{questionId}")
    public String show(@PathVariable Long questionId, Model model) {
        Question focusQuestion = questionRepository.findById(questionId).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("question", focusQuestion);
        return "qna/show";
    }
}
