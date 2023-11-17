package com.bitlab.final_project.repositories;

import com.bitlab.final_project.models.Course;
import com.bitlab.final_project.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    List<Course> findByNameContainingIgnoreCase(String searchTerm);

    Course findByName(String name);
}
