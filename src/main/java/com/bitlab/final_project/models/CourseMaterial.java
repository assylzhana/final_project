package com.bitlab.final_project.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;

@Entity
@Table(name = "course_material")
@Getter
@Setter
public class CourseMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_material_text",nullable = true, length = 100000)
    private String material;

    @OneToOne
    private Question questions;
}
