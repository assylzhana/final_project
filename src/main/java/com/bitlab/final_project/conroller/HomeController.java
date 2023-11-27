package com.bitlab.final_project.conroller;

import com.bitlab.final_project.models.*;
import com.bitlab.final_project.repositories.AnswerRepository;
import com.bitlab.final_project.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private ParagraphService paragraphService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AcademicNewsService academicNewsService;

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

    @GetMapping("/academic-news")
    public String news(Model model){
        List<AcademicNews> news =  academicNewsService.getNews();
        model.addAttribute("news",news);
        return "academic";
    }
    @PostMapping("/academic-news/add")
    public String addRequest(@RequestParam String title,@RequestParam String text,@RequestParam String inShort) {
        AcademicNews academicNews = new AcademicNews();
        academicNews.setTitle(title);
        academicNews.setText(text);
        academicNews.setInShort(inShort);
        academicNewsService.saveNews(academicNews);
        return "redirect:/academic-news";
    }

    @GetMapping("/dashboard/{courseName}/{paragraphName}/questions")
    public String getQuestionsForParagraph(
            @PathVariable String courseName,
            @PathVariable String paragraphName,
            Model model
    ) {
        Course course = courseService.getCourseByName(courseName);
        Paragraph paragraph = courseService.getParagraphByName(course, paragraphName);

        if (course != null && paragraph != null) {
            List<Question> questions = questionService.findByCourseAndParagraph(course, paragraph);
            model.addAttribute("questions", questions);
            model.addAttribute("course", course);
            model.addAttribute("paragraph", paragraph);
            Collections.sort(questions, Comparator.comparing(Question::getId));
            return "question";
        } else {
            return "redirect:/dashboard/"+courseName;
        }
    }

    @GetMapping("/dashboard/{courseName}/{paragraphName}/questions/{questionId}")
    public String getQuestionForParagraph(
            @PathVariable String courseName,
            @PathVariable String paragraphName,
            @PathVariable Long questionId,
            Model model) {

        Course course = courseService.getCourseByName(courseName);
        Paragraph paragraph = courseService.getParagraphByName(course, paragraphName);

        if (course != null && paragraph != null) {
            Question question = questionService.getQuestionById(questionId);
            if (question != null) {
                model.addAttribute("question", question);
                model.addAttribute("course", course);
                model.addAttribute("paragraph", paragraph);
                return "questionInfo";
            }
        }

        return "redirect:/dashboard/" + courseName +"/"+paragraphName;
    }

    @PostMapping("/dashboard/{courseName}/{paragraphName}/questions/edit/{questionId}")
    public String editQuestion(
            @PathVariable String courseName,
            @PathVariable String paragraphName,
            @PathVariable Long questionId,
            @RequestParam String questionText,
            @RequestParam String option1,
            @RequestParam String option2,
            @RequestParam String option3,
            @RequestParam String option4,
            @RequestParam int correctOptionIndex) {

        Question existingQuestion = questionService.getQuestionById(questionId);

        if (existingQuestion == null) {
            return "redirect:/dashboard/" + courseName + "/" + paragraphName + "/questions";
        }
        existingQuestion.setQuestionText(questionText);
        existingQuestion.getAnswer().setOption1(option1);
        existingQuestion.getAnswer().setOption2(option2);
        existingQuestion.getAnswer().setOption3(option3);
        existingQuestion.getAnswer().setOption4(option4);
        existingQuestion.getAnswer().setCorrectAnswerIndex(correctOptionIndex);

        questionService.saveQuestion(existingQuestion);

        return "redirect:/dashboard/" + courseName + "/" + paragraphName + "/questions";
    }

    @PostMapping("/dashboard/{courseName}/{paragraphName}/questions/delete/{questionId}")
    public String deleteQuestion(
            @PathVariable String courseName,
            @PathVariable String paragraphName,
            @PathVariable Long questionId) {
        Question question = questionService.getQuestionById(questionId);
        if (question.getAnswer() != null) {
            answerService.deleteAnswerById(question.getAnswer().getId());
        }

        questionService.deleteQuestionById(questionId);

        return "redirect:/dashboard/" + courseName + "/" + paragraphName + "/questions";

    }


    @PostMapping("/dashboard/{courseName}/{paragraphName}/questions/add")
    @Transactional
    public String addQuestion(
            @PathVariable String courseName,
            @PathVariable String paragraphName,
            @RequestParam String questionText,
            @RequestParam String option1,
            @RequestParam String option2,
            @RequestParam String option3,
            @RequestParam String option4,
            @RequestParam int correctOptionIndex) {

        Course course = courseService.getCourseByName(courseName);
        Paragraph paragraph = courseService.getParagraphByName(course, paragraphName);
        Question question = new Question();

        question.setCourse(course);
        question.setParagraph(paragraph);
        question.setQuestionText(questionText);

        questionService.saveQuestion(question);

        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setOption1(option1);
        answer.setOption2(option2);
        answer.setOption3(option3);
        answer.setOption4(option4);
        answer.setCorrectAnswerIndex(correctOptionIndex);

        answerService.saveAnswer(answer);
        System.out.println("sout results");
        System.out.println(question.getQuestionText());
        System.out.println(questionText);
        System.out.println(answer.getOption1());
        System.out.println(answer.getOption2());
        System.out.println(answer.getOption3());
        System.out.println(answer.getOption4());

        return "redirect:/dashboard/" + courseName + "/" + paragraphName + "/questions";
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
            @RequestParam(name = "paragraphName") String paragraphName,
            @RequestParam(name = "paragraphContent") String paragraphContent,
            Model model) {
        courseService.addParagraph(courseId, paragraphName, paragraphContent);
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
            @PathVariable("courseName") String courseName,
            @PathVariable("paragraphName") String paragraphName) {
        Course course = courseService.getCourseByName(courseName);
        if (course != null) {
            Paragraph paragraphToDelete = null;
            for (Paragraph paragraph : course.getParagraphs()) {
                if (paragraph.getName().equals(paragraphName)) {
                    paragraphToDelete = paragraph;
                    break;
                }
            }

            if (paragraphToDelete != null) {
                paragraphService.deleteParagraph(paragraphToDelete);
            }

        }
        return "redirect:/dashboard/" + courseName;
    }


    @PostMapping("/dashboard/{courseName}/{paragraphName}/edit")
    public String editParagraph(@PathVariable String courseName,
                                @PathVariable String paragraphName,
                                @RequestParam(name = "content") String content,
                                @RequestParam(name = "paragraphName") String updatedParagraphName) {

        Course course = courseService.getCourseByName(courseName);

        if (course != null) {
            Paragraph paragraph = courseService.getParagraphByName(course, paragraphName);

            if (paragraph != null) {
                paragraph.setContent(content);
                paragraph.setName(updatedParagraphName);

                courseService.saveParagraph(paragraph);

                return "redirect:/dashboard/" + courseName + "/" + updatedParagraphName;
            }
        }
        return "redirect:/dashboard/" + courseName;
    }


}

