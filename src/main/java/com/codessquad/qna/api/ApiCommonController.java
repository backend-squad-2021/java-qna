package com.codessquad.qna.api;

import com.codessquad.qna.question.Question;
import com.codessquad.qna.question.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiCommonController {

  @Autowired
  private QuestionRepository questionRepository;

  /**
   * Feat : 모든 Question 을 가져옵니다.
   * Desc : Get 처리를 위해 분리하였습니다.
   * Return : /welcome
   */
  @GetMapping("/{page}")
  public Result welcomeGet(@PathVariable int page) {
    Pageable firstPageWithTwoElements = PageRequest.of(page, 15, Sort.by("lastModifiedDateTime").descending());
    Page<Question> questions = questionRepository.findAllByDeleted(firstPageWithTwoElements, false);

    return Result.ok(questions);
  }
}
