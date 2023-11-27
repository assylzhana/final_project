package com.bitlab.final_project.repositories;

import com.bitlab.final_project.models.AcademicNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicNewsRepository extends JpaRepository<AcademicNews,Long> {
}
