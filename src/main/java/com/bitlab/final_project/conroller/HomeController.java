package com.bitlab.final_project.conroller;

import com.bitlab.final_project.models.Course;
import com.bitlab.final_project.models.CourseMaterial;
import com.bitlab.final_project.models.Paragraph;
import com.bitlab.final_project.models.User;
import com.bitlab.final_project.services.CourseService;
import com.bitlab.final_project.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
    @GetMapping("/question")
    public String question(){
        return "question";
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
        model.addAttribute("counter", new Counter());
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
        return "redirect:/dashboard/"+course.getName();
    }
    @PostMapping("/dashboard/delete/{id}")
    public String deleteCourse(@PathVariable Long id){
        courseService.deleteCourseById(id);
        return "redirect:/dashboard";
    }
    @PostMapping("/dashboard/add-paragraph/{courseId}")
    public String addParagraph(
            @PathVariable Long courseId,
            @RequestParam(name = "paragraphContent") String paragraphContent,
            Model model) {
        courseService.addParagraph(courseId, paragraphContent);
        return "redirect:/dashboard/" + courseService.getCourseById(courseId).getName();
    }


    private static class Counter {
        private int count = 0;

        public int getCount() {
            return ++count;
        }
    }

    @GetMapping("/dashboard/{courseName}/{paragraphName}")
    public String viewParagraph(
            @PathVariable String courseName,
            @PathVariable String paragraphName,
            Model model) {

        Course course = courseService.getCourseByName(courseName);


        if (course != null) {
            Paragraph paragraph = courseService.getParagraphByName(course, paragraphName);

            if (paragraph != null) {

                model.addAttribute("course", course);
                model.addAttribute("paragraph", paragraph);
                return "material";
            }
        }

        return "paragraph";
    }

    @PostMapping("/dashboard/{courseName}/{paragraphName}/delete")
    public String deleteParagraph(
            @PathVariable String courseName,
            @PathVariable String paragraphName) {

        Course course = courseService.getCourseByName(courseName);

        if (course != null) {
            Paragraph paragraphToDelete = courseService.getParagraphByName(course, paragraphName);

            if (paragraphToDelete != null) {
                course.getParagraphs().remove(paragraphToDelete);
                courseService.saveCourse(course);
            }
        }

        return "redirect:/dashboard/" + courseName;
    }

    @PostMapping("/dashboard/{courseName}/{paragraphName}/edit")
    public String editParagraph(HttpServletRequest request,
                                @PathVariable String courseName,
                                @PathVariable String paragraphName) {

        Course course = courseService.getCourseByName(courseName);

        if (course != null) {
            Paragraph paragraph = courseService.getParagraphByName(course, paragraphName);

            if (paragraph != null) {
                CourseMaterial courseMaterial = paragraph.getCourseMaterial();
                if (courseMaterial == null) {
                    courseMaterial = new CourseMaterial();
                    paragraph.setCourseMaterial(courseMaterial);
                }

                String material = request.getParameter("material");
                String updatedParagraphName = request.getParameter("paragraphName");

                paragraph.setContent(updatedParagraphName);

                courseMaterial.setMaterial(material);

                courseService.saveParagraph(paragraph);

                return "redirect:/dashboard/" + courseName + "/" + updatedParagraphName;
            }
        }
        return "redirect:/dashboard/" + courseName;
    }
}

