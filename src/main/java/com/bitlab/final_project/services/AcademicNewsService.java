package com.bitlab.final_project.services;

import com.bitlab.final_project.models.AcademicNews;
import com.bitlab.final_project.repositories.AcademicNewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcademicNewsService {
    @Autowired
    private AcademicNewsRepository academicNewsRepository;

    public List<AcademicNews> getNews() {
        return academicNewsRepository.findAll();
    }

    public void saveNews(AcademicNews academicNews) {
        academicNewsRepository.save(academicNews);
    }
}
