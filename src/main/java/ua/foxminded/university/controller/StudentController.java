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

import ua.foxminded.university.dto.GroupDTO;
import ua.foxminded.university.dto.StudentDTO;
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
    private ModelMapper modelMapper;

    @Autowired
    public StudentController(StudentService service, GroupService groupService, ModelMapper modelMapper) {
        this.service = service;
        this.groupService = groupService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String list(Model model) {
        List<Student> students = service.getAll();
        List<StudentDTO> studentDTOList = students.stream().map(student -> modelMapper.map(student, StudentDTO.class)).collect(Collectors.toList());
        model.addAttribute("students", studentDTOList);
        return "student/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        List<Group> groups = groupService.getAll();
        List<GroupDTO> groupDTOList = groups.stream().map(group -> modelMapper.map(group, GroupDTO.class)).collect(Collectors.toList());
        model.addAttribute("groups", groupDTOList);
        model.addAttribute("student", new StudentDTO());
        return "student/add";
    }

    @PostMapping("save")
    public String save(@Valid @ModelAttribute("student") StudentDTO studentDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "student/add";
        }
        Student student = modelMapper.map(studentDTO, Student.class);
        service.save(student);
        return "redirect:/students";
    }

    @PatchMapping("update")
    public String update(@Valid @ModelAttribute("student") StudentDTO studentDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Group> groups = groupService.getAll();
            List<GroupDTO> groupDTOList = groups.stream().map(group -> modelMapper.map(group, GroupDTO.class)).collect(Collectors.toList());
            model.addAttribute("groups", groupDTOList);
            return "student/edit";
        }
        Student student = modelMapper.map(studentDTO, Student.class);
        service.save(student);
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Student student = service.getById(id);
        StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);
        List<Group> groups = groupService.getAll();
        List<GroupDTO> groupDTOList = groups.stream().map(group -> modelMapper.map(group, GroupDTO.class)).collect(Collectors.toList());
        model.addAttribute("student", studentDTO);
        model.addAttribute("groups", groupDTOList);
        return "student/edit";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        service.deleteById(id);
        return "redirect:/students";
    }
}