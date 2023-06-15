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
import ua.foxminded.university.model.Group;
import ua.foxminded.university.service.GroupService;

import javax.validation.Valid;

@Controller
@RequestMapping("/groups")
public class GroupController {
    private GroupService service;

    @Autowired
    public GroupController(GroupService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        List<GroupDTO> groupDTOList = service.getAll();
        model.addAttribute("groups", groupDTOList);
        return "group/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("group", new GroupDTO());
        return "group/add";
    }

    @PostMapping("save")
    public String save(@Valid @ModelAttribute("group") GroupDTO groupDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "group/add";
        }
        service.save(groupDTO);
        return "redirect:/groups";
    }

    @PatchMapping("update")
    public String update(@Valid @ModelAttribute("group") GroupDTO groupDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "group/edit";
        }
        service.save(groupDTO);
        return "redirect:/groups";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        GroupDTO groupDTO = service.getById(id);
        model.addAttribute("group", groupDTO);
        return "group/edit";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        service.deleteById(id);
        return "redirect:/groups";
    }
}