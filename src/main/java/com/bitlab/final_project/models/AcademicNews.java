package com.bitlab.final_project.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "academic_news")
@Getter
@Setter
public class AcademicNews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "news_title", nullable = false,  length = 250, unique = true)
    private String title;

    @Column(name = "news_inShort", nullable = false, length = 3000)
    private String inShort;

    @Column(name = "news_text", nullable = false,  length = 20000)
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "news_date")
    private Date date;
    @PrePersist
    protected void onCreate() {
        date = new Date();
    }
    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
