package com.codesquad.qna.model;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User writer;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    @Column(nullable = false)
    @NotEmpty
    private String title;

    private String contents;

    @Column(nullable = false)
    private LocalDateTime createdDateTime;

    @Formula("(select count(*) from answer a where a.question_id = id and a.deleted = false)")
    private int countOfAnswers;

    @Column(nullable = false)
    private boolean deleted;

    public Question() {}

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDateTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getWriter() {
        return writer.getUserId();
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return HtmlDocumentUtils.getEntertoBrTag(this.contents);
    }

    public String getCreatedDateTimetoString() {
        return DateTimeFormatUtils.getFormattedLocalDateTime(this.createdDateTime);
    }

    public int getCountOfAnswers() {
        return countOfAnswers;
    }

    public boolean matchWriter(User sessionedUser) {
        return this.writer.equals(sessionedUser);
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public boolean delete() {
        if (countOfAnswers == 0)
            return (this.deleted = true);

        for (Answer answer : answers) {
            if (!answer.matchWriter(this.writer))
                return false;
        }
        return (this.deleted = true);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createdDateTime=" + getCreatedDateTimetoString() +
                '}';
    }
}
