package com.codessquad.qna.web.controllers;

import com.codessquad.qna.exceptions.NotFoundException;
import com.codessquad.qna.exceptions.PermissionDeniedException;
import com.codessquad.qna.exceptions.UnauthorizedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

//TODO 개발환경(profile)에 따라 동작 방식 변경
// 1. exception stack trace 화면 출력 유무
// 2. 파일 로깅 유무

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {
    private Logger logger = LogManager.getLogger(ExceptionHandlerControllerAdvice.class);

    @ExceptionHandler(UnauthorizedException.class)
    public ModelAndView handleUnauthorizedException(Exception exception) {
        logger.error(exception);

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.setViewName("auth/loginForm");

        return mav;
    }

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFoundException(Exception exception) {
        logger.error(exception);

        ModelAndView mav = new ModelAndView();
        mav.addObject("status", HttpStatus.NOT_FOUND);
        mav.addObject( "exception", exception);
        mav.setViewName("error");

        return mav;
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ModelAndView handleForbiddenException(Exception exception) {
        logger.error(exception);

        ModelAndView mav = new ModelAndView();
        mav.addObject("status", HttpStatus.FORBIDDEN);
        mav.addObject("exception", exception);
        mav.setViewName("error");

        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllExceptions(Exception exception) {
        logger.error(exception);

        ModelAndView mav = new ModelAndView();
        mav.addObject("status", HttpStatus.INTERNAL_SERVER_ERROR);
        mav.addObject("exception", exception);
        mav.setViewName("error");

        return mav;
    }
}
