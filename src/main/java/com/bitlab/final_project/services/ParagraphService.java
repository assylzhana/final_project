package com.bitlab.final_project.services;

import com.bitlab.final_project.models.Course;
import com.bitlab.final_project.models.Paragraph;
import com.bitlab.final_project.models.Question;
import com.bitlab.final_project.repositories.AnswerRepository;
import com.bitlab.final_project.repositories.CourseRepository;
import com.bitlab.final_project.repositories.ParagraphRepository;
import com.bitlab.final_project.repositories.QuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParagraphService {
    @Autowired
    private ParagraphRepository paragraphRepository;

     @Autowired
     private CourseRepository courseRepository;
     @Autowired
     private QuestionRepository questionRepository;
     @Autowired
     private AnswerRepository answerRepository;


    @Transactional
    public void deleteParagraph(Paragraph paragraph) {
        Course course = paragraph.getCourse();

        if (course != null) {
            // Remove the paragraph from the associated course
            course.getParagraphs().remove(paragraph);

            // Delete associated questions and answers
            List<Question> questions = questionRepository.findByParagraph(paragraph);
            for (Question question : questions) {
                answerRepository.deleteByQuestion(question);
                questionRepository.delete(question);
            }

            // Delete the paragraph itself
            paragraphRepository.delete(paragraph);
        }
    }

}
