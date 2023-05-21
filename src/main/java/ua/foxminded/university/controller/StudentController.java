package ua.foxminded.university.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.university.model.Group;
import ua.foxminded.university.model.Student;
import ua.foxminded.university.service.GroupService;
import ua.foxminded.university.service.StudentService;

import javax.validation.Valid;

@Controller
@RequestMapping("/students")
public class StudentController {
    private StudentService service;
    private GroupService groupService;

    @Autowired
    public StudentController(StudentService service, GroupService groupService) {
        this.service = service;
        this.groupService = groupService;
    }

    @GetMapping
    public String list(Model model) {
        List<Student> students = service.getAll();
        model.addAttribute("students", students);
        return "student/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        List<Group> groups = groupService.getAll();
        model.addAttribute("groups", groups);
        model.addAttribute("student", new Student());
        return "student/add";
    }

    @PostMapping("save")
    public String save(@Valid @ModelAttribute("student") Student student, BindingResult result) {
        if (result.hasErrors()) {
            return "student/add";
        }
        service.save(student);
        return "redirect:/students";
    }

    @PatchMapping("update")
    public String update(@Valid @ModelAttribute("student") Student student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Group> groups = groupService.getAll();
            model.addAttribute("groups", groups);
            return "student/edit";
        }
        service.save(student);
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Student student = service.getById(id);
        List<Group> groups = groupService.getAll();
        model.addAttribute("student", student);
        model.addAttribute("groups", groups);
        return "student/edit";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        service.deleteById(id);
        return "redirect:/students";
    }
}