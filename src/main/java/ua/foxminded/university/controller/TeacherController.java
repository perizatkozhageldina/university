package ua.foxminded.university.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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

import ua.foxminded.university.dto.TeacherDTO;
import ua.foxminded.university.model.Teacher;
import ua.foxminded.university.service.TeacherService;

import javax.validation.Valid;

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
        List<TeacherDTO> teacherDTOList = service.getAll();
        model.addAttribute("teachers", teacherDTOList);
        return "teacher/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("teacher", new TeacherDTO());
        return "teacher/add";
    }

    @PostMapping("save")
    public String save(@Valid @ModelAttribute("teacher") TeacherDTO teacherDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "teacher/add";
        }
        service.save(teacherDTO);
        return "redirect:/teachers";
    }

    @PatchMapping("update")
    public String update(@Valid @ModelAttribute("teacher") TeacherDTO teacherDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "teacher/edit";
        }
        service.save(teacherDTO);
        return "redirect:/teachers";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        TeacherDTO teacherDTO = service.getById(id);
        model.addAttribute("teacher", teacherDTO);
        return "teacher/edit";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        service.deleteById(id);
        return "redirect:/teachers";
    }
}