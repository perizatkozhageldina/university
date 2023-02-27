package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ua.foxminded.university.model.Course;
import ua.foxminded.university.service.CourseService;

@Controller
public class CourseController {
    
    @Autowired
    CourseService service;
    
    @GetMapping("/course")
    public String showCoursesList(Model model) {
        model.addAttribute("courses", service.getAll());
        return "course";      
    }
    
    @GetMapping("/add")
    public String showAddForm() {
        return "add";
    }
    
    @PostMapping("/addCourse")
    public String submitData(@ModelAttribute("course") Course course) {
        System.out.println("in controller " + course);
        service.add(course);
        return "course";
    }
    
//    @PostMapping("/addCourse")
//    public String addCourse(Course  course, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            return "course";
//        }
//
//        service.add(course);
//        return "redirect:course";
//    }
}
