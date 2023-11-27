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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private ParagraphRepository paragraphRepository;

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private  ParagraphService paragraphService;

    public  void addNewCourse(Course newCourse) {
        courseRepository.save(newCourse);
    }

    public Course addCourse(Course course){
        return courseRepository.save(course);
    }
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public List<Course> searchCourses(String searchTerm) {
        return courseRepository.findByNameContainingIgnoreCase(searchTerm);
    }

    public Course getCourseByName(String name) {
        return courseRepository.findByName(name);
    }
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }
    public Course editCourse(Course course){
        return courseRepository.save(course);
    }

    @Transactional
    public void deleteCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);

        if (course != null) {
            for (Paragraph paragraph : new ArrayList<>(course.getParagraphs())) {
                paragraphService.deleteParagraph(paragraph);
            }
            courseRepository.delete(course);
        }
    }



    public void addParagraph(Long courseId, String paragraphName, String paragraphContent) {
        Course course = courseRepository.findById(courseId).orElse(null);

        Paragraph paragraph = new Paragraph();
        paragraph.setName(paragraphName);
        paragraph.setContent(paragraphContent);
        paragraph.setCourse(course);

        paragraphRepository.save(paragraph);
    }




    public void saveParagraph(Paragraph paragraph) {
        paragraphRepository.save(paragraph);
    }

    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    public Paragraph getParagraphByName(Course course, String paragraphName) {
        Paragraph p = paragraphRepository.findByCourseAndName(course, paragraphName);
        return p;
    }


}
