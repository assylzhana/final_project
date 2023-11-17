package com.bitlab.final_project.repositories;


import com.bitlab.final_project.models.Course;
import com.bitlab.final_project.models.Paragraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ParagraphRepository extends JpaRepository<Paragraph,Long> {

    Paragraph findByCourseAndContent(Course course, String paragraphName);
}
