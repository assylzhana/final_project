package com.bitlab.final_project.services;

import com.bitlab.final_project.models.Course;
import com.bitlab.final_project.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

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
}
