package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.dto.GroupDTO;
import ua.foxminded.university.dto.StudentDTO;
import ua.foxminded.university.service.GroupService;
import ua.foxminded.university.service.StudentService;

import javax.validation.Valid;
import java.util.List;

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
        List<StudentDTO> studentDTOList = service.getAll();
        model.addAttribute("students", studentDTOList);
        return "student/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        List<GroupDTO> groupDTOList = groupService.getAll();
        model.addAttribute("groups", groupDTOList);
        model.addAttribute("student", new StudentDTO());
        return "student/add";
    }

    @PostMapping("save")
    public String save(@Valid @ModelAttribute("student") StudentDTO studentDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<GroupDTO> groupDTOList = groupService.getAll();
            model.addAttribute("groups", groupDTOList);
            return "student/add";
        }
        service.save(studentDTO);
        return "redirect:/students";
    }

    @PatchMapping("update")
    public String update(@Valid @ModelAttribute("student") StudentDTO studentDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<GroupDTO> groupDTOList = groupService.getAll();
            model.addAttribute("groups", groupDTOList);
            return "student/edit";
        }
        service.save(studentDTO);
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        StudentDTO studentDTO = service.getById(id);
        List<GroupDTO> groupDTOList = groupService.getAll();
        model.addAttribute("student", studentDTO);
        model.addAttribute("groups", groupDTOList);
        return "student/edit";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        service.deleteById(id);
        return "redirect:/students";
    }
}//