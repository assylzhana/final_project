package com.bitlab.final_project.services;

import com.bitlab.final_project.repositories.ParagraphRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParagraphService {
    @Autowired
    private ParagraphRepository paragraphRepository;
}
