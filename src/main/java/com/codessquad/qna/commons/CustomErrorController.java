package com.codessquad.qna.commons;

import com.codessquad.qna.errors.ForbiddenException;
import com.codessquad.qna.errors.QuestionException;
import com.codessquad.qna.errors.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomErrorController {

  /**
   * Feat : 특정되지 않은 예외 처리를 합니다.
   * Desc :
   * Return : /error
   */
  @ExceptionHandler(Exception.class)
  public String defaultException(Exception e, HttpStatus httpStatus, Model model) {
    log.info("### defaultException() ");

    model.addAttribute("code", httpStatus.value());
    model.addAttribute("msg", httpStatus.getReasonPhrase());
    model.addAttribute("descMsg", e.getMessage());

    return "/error";
  }

  /**
   * Feat : ForbiddenException 예외 처리를 합니다.
   * Desc :
   * Return : /error
   */
  @ExceptionHandler(ForbiddenException.class)
  public String forbiddenException(Exception e, Model model) {
    log.info("### forbiddenException() ");

    model.addAttribute("code", HttpStatus.FORBIDDEN.value());
    model.addAttribute("msg", HttpStatus.FORBIDDEN.getReasonPhrase());
    model.addAttribute("descMsg", e.getMessage());

    return "/error";
  }

  /**
   * Feat : UserException 예외 처리를 합니다.
   * Desc :
   * Return : /error
   */
  @ExceptionHandler(UserException.class)
  public String userException(Exception e, Model model) {
    log.info("### userException() ");
    if (e.getMessage().equals(CustomErrorCode.BAD_REQUEST.getMsg())) {
      model.addAttribute("code", HttpStatus.BAD_REQUEST.value());
      model.addAttribute("msg", HttpStatus.BAD_REQUEST.getReasonPhrase());
      model.addAttribute("descMsg", e.getMessage());

      return "/error";
    } else if (e.getMessage().equals(CustomErrorCode.USER_NOT_EXIST.getMsg())) {
      model.addAttribute("notExistUser", true);
    } else if (e.getMessage().equals(CustomErrorCode.USER_NOT_LOGIN.getMsg())) {
      model.addAttribute("requestLogin", true);
    } else if (e.getMessage().equals(CustomErrorCode.USER_NOT_MATCHED_PASSWORD.getMsg())) {
      model.addAttribute("wrongPassword", true);
    }

    return "/users/login";
  }

  /**
   * Feat : QuestionException 예외 처리를 합니다.
   * Desc :
   * Return : /error
   */
  @ExceptionHandler(QuestionException.class)
  public String questionException(Exception e, Model model) {
    log.info("### questionException() ");
    if (e.getMessage().equals(CustomErrorCode.BAD_REQUEST.getMsg())) {
      model.addAttribute("code", HttpStatus.BAD_REQUEST.value());
      model.addAttribute("msg", HttpStatus.BAD_REQUEST.getReasonPhrase());
      model.addAttribute("descMsg", e.getMessage());

      return "/error";
    } else if (e.getMessage().equals(CustomErrorCode.USER_NOT_MATCHED.getMsg())) {
      model.addAttribute("notMatchedUser", true);

      return "redirect:/";
    }

    return "/error";
  }
}