package com.codessquad.qna.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable=false)
    private String title;

    @Lob
    private String contents;
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "question")
    @OrderBy("id ASC")
    private List<Answer> answers;

    public Question() {}

    public List<Answer> getAnswers() {
        return answers;
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }
    public User getWriter() { return writer; }
    public String getTitle() { return title; }
    public String getContents() { return contents; }

    public void setId(Long id) { this.id = id; }
    public void setWriter(User writer) { this.writer = writer; }
    public void setTitle(String title) { this.title = title; }
    public void setContents(String contents) { this.contents = contents; }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public String getFormattedCreatedDate() {
        if (createdDate == null) {
            return "";
        }
        return createdDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    public boolean isSameWriter(User loginUser) {
        return this.writer.equals(loginUser);
    }

    @Override
    public String toString() {
        return "Question {" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
