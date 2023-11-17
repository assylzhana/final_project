package com.bitlab.final_project.services;

import com.bitlab.final_project.models.Course;
import com.bitlab.final_project.models.Paragraph;
import com.bitlab.final_project.repositories.CourseRepository;
import com.bitlab.final_project.repositories.ParagraphRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ParagraphRepository paragraphRepository;

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

    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
    }
    @Transactional
    public void addParagraph(Long courseId, String paragraphContent) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));

        Paragraph paragraph = new Paragraph();
        paragraph.setContent(paragraphContent);
        paragraph.setCourse(course);

        course.getParagraphs().add(paragraph);

        courseRepository.save(course);
    }

    public Paragraph getParagraphByName(Course course, String paragraphName) {
        return paragraphRepository.findByCourseAndContent(course, paragraphName);
    }

    public void saveParagraph(Paragraph paragraph) {
        paragraphRepository.save(paragraph);
    }

    public void saveCourse(Course course) {
        courseRepository.save(course);
    }


}
