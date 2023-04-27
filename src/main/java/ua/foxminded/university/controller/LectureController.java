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

import ua.foxminded.university.model.Lecture;
import ua.foxminded.university.service.LectureService;

@Controller
@RequestMapping("/lectures")
public class LectureController {
    private LectureService service;

    @Autowired
    public LectureController(LectureService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        List<Lecture> lectures = service.getAll();
        model.addAttribute("lectures", lectures);
        return "lecture/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("lecture", new Lecture());
        return "lecture/add";
    }

    @PostMapping("save")
    public String save(@ModelAttribute("lecture") Lecture lecture) {
        service.save(lecture);
        return "redirect:/lectures";
    }

    @PatchMapping("update")
    public String update(@ModelAttribute("lecture") Lecture lecture) {
        service.save(lecture);
        return "redirect:/lectures";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Lecture lecture = service.getById(id);
        model.addAttribute("lecture", lecture);
        return "lecture/edit";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        service.deleteById(id);
        return "redirect:/lectures";
    }
}
