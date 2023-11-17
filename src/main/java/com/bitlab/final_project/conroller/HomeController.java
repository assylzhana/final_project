package com.bitlab.final_project.conroller;

import com.bitlab.final_project.models.Course;
import com.bitlab.final_project.models.Paragraph;
import com.bitlab.final_project.models.User;
import com.bitlab.final_project.services.CourseService;
import com.bitlab.final_project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller

public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/")
    public String homePage(){
        return "home";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";}

    @GetMapping("/sign-up")
    public String signUpPage(){
        return "sign-up";
    }
    @PostMapping("/sign-up")
    public String signUp(User user, @RequestParam(name = "re_password") String rePassword){

        return "redirect:/";
    }


    @GetMapping("/panel")
    public String panel(){
        return "panel";
    }
    @GetMapping("/rating")
    public String rating(){
        return "rating";
    }
    @GetMapping("/support")
    public String support(){
        return "support";
    }
    @PostMapping("/sign-out")
    public String signOut(){
        return "redirect:/";
    }
    @GetMapping("/settings")
    public String settings(){
        return "settings";
    }
    @PostMapping("/delete-account")
    public String delete(){
        return "redirect:/";
    }


    @GetMapping("/dashboard")
    public String dashboard(Model model, @RequestParam(required = false) String searchTerm) {
        List<Course> courses;
        if (searchTerm != null && !searchTerm.isEmpty()) {
            courses = courseService.searchCourses(searchTerm);
        } else {
            courses = courseService.getCourses();
        }
        Collections.sort(courses, Comparator.comparing(Course::getId));
        model.addAttribute("courses", courses);
        model.addAttribute("roles",userService.getUsers());
        return "dashboard";
    }
    @PostMapping("/dashboard/add-course")
    public String addCourse(Course newCourse){
        courseService.addNewCourse(newCourse);
        return "redirect:/dashboard";
    }
    @GetMapping("/dashboard/{name}")
    public String paragraph(@PathVariable String name, Model model) {
        Course course = courseService.getCourseByName(name);

        if (course != null) {
            List<Paragraph> paragraphs = course.getParagraphs();
            model.addAttribute("course", course);
            model.addAttribute("paragraphs", paragraphs);
            model.addAttribute("counter", new Counter());
            return "paragraph";
        } else {
            return "dashboard";
        }
    }
    @PostMapping("/dashboard/edit/{id}")
    public String editCourse(@PathVariable Long id, @RequestParam(name = "name") String name,
                             @RequestParam(name = "explanation") String explanation){
        Course course = courseService.getCourseById(id);
        course.setName(name);
        course.setExplanation(explanation);
        courseService.editCourse(course);
        return "redirect:/dashboard";
    }

    private static class Counter {
        private int count = 0;

        public int getCount() {
            return ++count;
        }
    }



    @GetMapping("/question")
    public String question(){
        return "question";
    }
}
