package com.bitlab.final_project.repositories;

import com.bitlab.final_project.models.Course;
import com.bitlab.final_project.models.Paragraph;
import com.bitlab.final_project.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long > {
    List<Question> findByCourseAndParagraph(Course course, Paragraph paragraph);

    List<Question> findByParagraph(Paragraph paragraph);
}
