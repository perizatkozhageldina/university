package ua.foxminded.university.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.university.model.Student;
import ua.foxminded.university.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService service;

    @GetMapping("")
    public String list(Model model) {
        List<Student> students = service.getAll();
        model.addAttribute("students", students);
        return "student/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("student", new Student());
        return "student/add";
    }

    @PostMapping("save")
    public String save(@ModelAttribute("student") Student student) {
        service.add(student);
        return "redirect:/students";
    }
    
    @PostMapping("update")
    public String update(@ModelAttribute("student") Student student) {
        service.update(student);
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Student student = service.getById(id);
        model.addAttribute("student", student);
        return "student/edit";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        service.deleteById(id);
        return "redirect:/students";
    }
}