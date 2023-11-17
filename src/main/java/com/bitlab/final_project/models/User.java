package com.bitlab.final_project.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import java.util.Set;

@Controller
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
    private String email;
    private String phone;
    private String address;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

}
