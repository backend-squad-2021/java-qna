package com.codessquad.qna.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserJPATest {

  @Autowired
  private UserRepository userRepository;

  @Test
  void GET_USERS() {
    List<User> users = userRepository.findAll();
    int expectedUserSize = 8;

    assertEquals(expectedUserSize, users.size());
  }
}
