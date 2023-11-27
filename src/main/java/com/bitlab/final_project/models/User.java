package com.bitlab.final_project.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(name = "user_email", unique = true)
    private String email;

    @Column(name = "user_phone")
    private String phone;
    private String address;
    private String password;
    private Long score;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private StudentGroup group;

}
