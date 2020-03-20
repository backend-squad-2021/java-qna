package com.codessquad.qna.commons;

import com.codessquad.qna.answer.Answer;
import com.codessquad.qna.answer.AnswerRepository;
import com.codessquad.qna.errors.QuestionException;
import com.codessquad.qna.errors.UserException;
import com.codessquad.qna.question.Question;
import com.codessquad.qna.question.QuestionRepository;
import com.codessquad.qna.user.User;
import com.codessquad.qna.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class CommonUtils {

  @Autowired
  UserRepository userRepository;

  /**
   * Feat : Null 을 처리한 User 를 가져옵니다.
   * Desc :
   * Return : id 에 매칭되는 User
   */
  public User getUser(Long id) {
    Optional<User> optionalUser = userRepository.findById(id);
    User user = optionalUser.orElseThrow(() -> new UserException(CustomMessage.USER_NOT_EXIST));

    return user;
  }

  /**
   * Feat : Null 을 처리한 sessionedUser 를 가져옵니다.
   * Desc :
   * Return : sessionedUser
   */
  public static User getSessionedUser(HttpSession session) {
    Optional<Object> optionalUser = Optional.ofNullable(session.getAttribute("sessionedUser"));
    Object user = optionalUser.orElseThrow(() -> new UserException(CustomErrorCode.USER_NOT_LOGIN));

    return (User) user;
  }

  /**
   * Feat : login 여부를 체크합니다.
   * Desc : sessionedUser 가 없는 경우 에러를 처리합니다.
   * Return : true
   */
  public static boolean checkLoginOrError(HttpSession session) {
    getSessionedUser(session);
    return true;
  }

  /**
   * Feat : Null 을 처리한 Question 을 가져옵니다.
   * Desc :
   * Return : id 에 매칭되는 question
   */
  public static Question getQuestion(QuestionRepository questionRepository, Long id) {
    Optional<Question> optionalQuestion = questionRepository.findByIdAndDeleted(id, false);
    Question question = optionalQuestion.orElseThrow(() -> new QuestionException(CustomErrorCode.QUESTION_NOT_EXIST));

    return question;
  }

  /**
   * Feat : Null 을 처리한 Answer 을 가져옵니다.
   * Desc :
   * Return : id 에 매칭되는 answer
   */
  public static Answer getAnswer(AnswerRepository answerRepository, Long id) {
    Optional<Answer> optionalAnswer = answerRepository.findByIdAndDeleted(id, false);
    Answer answer = optionalAnswer.orElseThrow(() -> new QuestionException(CustomErrorCode.ANSWER_NOT_EXIST));

    return answer;
  }

  /**
   * Feat : Answers 를 가져옵니다.
   * Desc :
   * Return : Question 에 매칭되는  answers
   */
  public static List<Answer> getAnswers(AnswerRepository answerRepository, Question question) {
    return answerRepository.findByQuestionIdAndDeleted(question.getId(), false);
  }
}
