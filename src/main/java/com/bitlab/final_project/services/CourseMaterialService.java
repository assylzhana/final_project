package com.bitlab.final_project.services;

import com.bitlab.final_project.repositories.CourseMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseMaterialService {
    @Autowired
    CourseMaterialRepository courseMaterialRepository;
}
