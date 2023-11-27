package com.bitlab.final_project.services;

import com.bitlab.final_project.models.Course;
import com.bitlab.final_project.models.Paragraph;
import com.bitlab.final_project.models.Question;
import com.bitlab.final_project.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> findByCourseAndParagraph(Course course, Paragraph paragraph) {
        return questionRepository.findByCourseAndParagraph(course, paragraph);
    }

    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    public void deleteQuestionById(Long questionId) {
        questionRepository.deleteById(questionId);
    }

    public Question getQuestionById(Long questionId) {
        return questionRepository.findById(questionId).orElse(null);
    }
}
