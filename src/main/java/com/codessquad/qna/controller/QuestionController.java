package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {
    private static final Logger LOGGER = LogManager.getLogger(QuestionController.class);

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/qna/form")
    public String getQuestionFrom() {
        LOGGER.info("[page]질문작성 폼");
        return "qna/form";
    }

    @PostMapping("/qna/create")
    public String createQna(Question question) {
        LOGGER.info("[page]질문 작성");
        question.setWriteTime();
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping()
    public String getIndex(Model model) {
        LOGGER.info("[page]메인");
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/qna/{id}")
    public String getQuestion(@PathVariable Long id, Model model) throws NotFoundException {
        LOGGER.info("[page]질문 상세보기");
        model.addAttribute("question", questionRepository.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 질문입니다.")));
        return "qna/show";
    }
}
