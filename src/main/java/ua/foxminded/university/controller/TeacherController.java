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
    private ModelMapper modelMapper;

    @Autowired
    public TeacherController(TeacherService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String list(Model model) {
        List<Teacher> teachers = service.getAll();
        List<TeacherDTO> teacherDTOList = teachers.stream().map(teacher -> modelMapper.map(teacher, TeacherDTO.class)).collect(Collectors.toList());
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
        Teacher teacher = modelMapper.map(teacherDTO, Teacher.class);
        service.save(teacher);
        return "redirect:/teachers";
    }

    @PatchMapping("update")
    public String update(@Valid @ModelAttribute("teacher") TeacherDTO teacherDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "teacher/edit";
        }
        Teacher teacher = modelMapper.map(teacherDTO, Teacher.class);
        service.save(teacher);
        return "redirect:/teachers";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Teacher teacher = service.getById(id);
        TeacherDTO teacherDTO = modelMapper.map(teacher, TeacherDTO.class);
        model.addAttribute("teacher", teacherDTO);
        return "teacher/edit";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        service.deleteById(id);
        return "redirect:/teachers";
    }
}