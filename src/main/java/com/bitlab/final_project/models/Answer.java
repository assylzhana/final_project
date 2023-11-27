package com.bitlab.final_project.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "answers")
@Getter
@Setter
public class Answer{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
    @Column(name = "option1", nullable = false)
    private String option1;
    @Column(name = "option2", nullable = false)
    private String option2;
    @Column(name = "option3", nullable = false)
    private String option3;
    @Column(name = "option4", nullable = false)
    private String option4;
    @Column(name = "correct_answer_index", nullable = false)
    private int correctAnswerIndex;
}
