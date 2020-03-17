package com.codesquad.qna.web;

import com.codesquad.qna.domain.Answer;
import com.codesquad.qna.domain.Question;
import com.codesquad.qna.domain.User;
import com.codesquad.qna.service.AnswerService;
import com.codesquad.qna.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.RollbackException;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;

import static com.codesquad.qna.UrlStrings.REDIRECT_MAIN;
import static com.codesquad.qna.UrlStrings.REDIRECT_QUESTION_SHOW;
import static com.codesquad.qna.web.HttpSessionUtils.getUserFromSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @PostMapping("")
    public String create(@PathVariable Long questionId, HttpSession session, String contents) {
        User loginUser = getUserFromSession(session);
        Question question = questionService.findById(questionId);
        Answer answer = new Answer(loginUser, question, contents);
        answerService.save(answer);
        return REDIRECT_QUESTION_SHOW.getUrl();
    }

    @GetMapping("/{id}/update")
    public String updateForm(@PathVariable Long questionId, @PathVariable Long id, HttpSession session, Model model) {
        User loginUser = getUserFromSession(session);
        Answer updatingAnswer = answerService.findByQuestionIdAndId(questionId, id);
        loginUser.hasPermission(updatingAnswer);
        model.addAttribute("updatingAnswer", updatingAnswer);
        return "qna/replyForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long questionId, @PathVariable Long id, Answer updatedAnswer, HttpSession session, Model model) {
        User loginUser = getUserFromSession(session);
        Answer updatingAnswer = answerService.findByQuestionIdAndId(questionId, id);
        loginUser.hasPermission(updatingAnswer);
        answerService.update(updatingAnswer, updatedAnswer);
        return REDIRECT_QUESTION_SHOW.getUrl();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        User loginUser = getUserFromSession(session);
        Answer answer = answerService.findByQuestionIdAndId(questionId, id);
        loginUser.hasPermission(answer);
        answerService.delete(answer);
        return REDIRECT_QUESTION_SHOW.getUrl();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String noInputWhenCreate() {
        return REDIRECT_MAIN.getUrl();
    }

    @ExceptionHandler(RollbackException.class)
    public String noInputWhenUpdate() {
        return REDIRECT_MAIN.getUrl();
    }
}
