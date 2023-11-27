package com.bitlab.final_project.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;

import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@Setter
public class Question{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "paragraph_id", nullable = false)
    private Paragraph paragraph;

    @Column(name = "question_text", nullable = false, unique = true)
    private String questionText;

    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL)
    private Answer answer;

}
