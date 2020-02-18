package com.codessquad.qna.controller;

import com.codessquad.qna.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class QuestionController {
    private List<Question> questions = new ArrayList<>();

    @PostMapping("/qna/create")
    public String createQna(Question question) {
        question.setTime(getTiime());
        questions.add(question);
        System.out.println(question);
        return "redirect:/";
    }

    @GetMapping()
    public String getIndex(Model model) {
        model.addAttribute("questions", questions);
        return "index";
    }

    public String getTiime() {
        SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
        Date time = new Date();
        return format.format(time);
    }

}
