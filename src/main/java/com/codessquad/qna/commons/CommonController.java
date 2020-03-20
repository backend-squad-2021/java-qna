package com.codessquad.qna.commons;

import com.codessquad.qna.question.Question;
import com.codessquad.qna.question.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Controller
public class CommonController {

  @Autowired
  private QuestionRepository questionRepository;

  /**
   * Feat : 시작 페이지로 들어오면 1페이지를 보여주도록 redirect 합니다.
   * Desc :
   * Return : redirect:/1
   */
  @GetMapping(value = {"/", ""})
  public String welcomeGet() {
    return "redirect:/1";
  }

  /**
   * Feat : pageCount 를 받아 해당 페이지의 글을 가져옵니다.
   * Desc : Get 처리를 위해 분리하였습니다.
   * Return : /welcome
   */
  @GetMapping("/{pageCount}")
  public String welcomeGetWithPageCount(@PathVariable String pageCount, Model model) {
    final int MAX_PAGE_COUNT_SHOW = 5;
    int pageCountInteger = Integer.parseInt(pageCount);

    Pageable firstPageWithTwoElements =
        PageRequest.of(pageCountInteger - 1, 15, Sort.by("lastModifiedDateTime").descending());
    Page<Question> questions = questionRepository.findAllByDeleted(firstPageWithTwoElements, false);
    model.addAttribute("questions", questions.getContent());

    Map<Integer, String> pageCountMap = new LinkedHashMap<>();
    for (int i = questions.getNumber(); i < questions.getTotalPages() && i < questions.getNumber() + 5; i++) {
      pageCountMap.put(i + 1, "http://localhost:8080/" + (i + 1));
    }
    model.addAttribute("pageCountMap", pageCountMap);

    String pageBeforeButton = "http://localhost:8080/" +
                              ((pageCountInteger > MAX_PAGE_COUNT_SHOW)
                               ? pageCountInteger - MAX_PAGE_COUNT_SHOW
                               : 1);
    model.addAttribute("pageBeforeButton", pageBeforeButton);

    String pageNextButton = "http://localhost:8080/" +
                            ((pageCountInteger + 5 > questions.getTotalPages())
                             ? questions.getTotalPages() :
                             pageCountInteger + MAX_PAGE_COUNT_SHOW);
    model.addAttribute("pageNextButton", pageNextButton);

    return "/welcome";
  }

  /**
   * Feat : 모든 Question 을 가져옵니다.
   * Desc : Post 처리를 위해 분리하였습니다.
   * Return : /welcome
   */
  @PostMapping("/welcome")
  public String welcomePost(Model model) {
    Pageable firstPageWithTwoElements = PageRequest.of(0, 15, Sort.by("lastModifiedDateTime").descending());
    Page<Question> questions = questionRepository.findAllByDeleted(firstPageWithTwoElements, false);
    model.addAttribute("questions", questions.getContent());
    model.addAttribute("pagesCount", questions.getTotalPages());
    return "/welcome";
  }
}
