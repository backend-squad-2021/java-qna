package com.codessquad.qna.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;
import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    List<Question> findAllByDeletedFalse();

    @Modifying
    @Transactional
    @Query(value = "UPDATE Question SET deleted=true WHERE id=:id")
    void delete(Long id);
}
