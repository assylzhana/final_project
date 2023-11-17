package com.bitlab.final_project.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;

@Entity
@Table(name = "course_material")
@Getter
@Setter
public class Question{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Course course;
}
