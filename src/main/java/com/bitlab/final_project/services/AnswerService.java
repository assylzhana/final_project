package com.bitlab.final_project.services;

import com.bitlab.final_project.models.Answer;
import com.bitlab.final_project.repositories.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    public void saveAnswer(Answer answer) {
        answerRepository.save(answer);
    }

    public void deleteAnswerById(Long id) {
        answerRepository.deleteById(id);
    }
}
