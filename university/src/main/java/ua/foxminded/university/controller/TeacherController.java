package ua.foxminded.university.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.university.model.Teacher;
import ua.foxminded.university.service.TeacherService;

@Controller
@RequestMapping("/teachers")
public class TeacherController {
    private TeacherService service;

    @Autowired
    public TeacherController(TeacherService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        List<Teacher> teachers = service.getAll();
        model.addAttribute("teachers", teachers);
        return "teacher/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("teacher", new Teacher());
        return "teacher/add";
    }

    @PostMapping("save")
    public String save(@ModelAttribute("teacher") Teacher teacher) {
        service.add(teacher);
        return "redirect:/teachers";
    }

    @PatchMapping("update")
    public String update(@ModelAttribute("teacher") Teacher teacher) {
        service.update(teacher);
        return "redirect:/teachers";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Teacher teacher = service.getById(id);
        model.addAttribute("teacher", teacher);
        return "teacher/edit";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        service.deleteById(id);
        return "redirect:/teachers";
    }
}