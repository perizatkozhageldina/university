package ua.foxminded.university.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        List<Group> groups = service.getAll();
        model.addAttribute("groups", groups);
        return "group/index";
    }

    @GetMapping("/add")
    public String add(@Valid Model model) {
        model.addAttribute("group", new Group());
        return "group/add";
    }

    @PostMapping("save")
    public String save(@Valid @ModelAttribute("group") Group group, BindingResult result) {
        if (result.hasErrors()) {
            return "group/add";
        }
        service.save(group);
        return "redirect:/groups";
    }

    @PatchMapping("update")
    public String update(@Valid @ModelAttribute("group") Group group, BindingResult result) {
        if (result.hasErrors()) {
            return "group/edit";
        }
        service.save(group);
        return "redirect:/groups";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Group group = service.getById(id);
        model.addAttribute("group", group);
        return "group/edit";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        service.deleteById(id);
        return "redirect:/groups";
    }
}