package com.bitlab.final_project.repositories;


import com.bitlab.final_project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    User findByEmail(String email);

}

