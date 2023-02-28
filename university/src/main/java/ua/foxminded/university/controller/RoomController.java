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

import ua.foxminded.university.model.Room;
import ua.foxminded.university.service.RoomService;

@Controller
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService service;

    @GetMapping("")
    public String list(Model model) {
        List<Room> rooms = service.getAll();
        model.addAttribute("rooms", rooms);
        return "room/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("room", new Room());
        return "room/add";
    }

    @PostMapping("save")
    public String save(@ModelAttribute("room") Room room) {
        service.add(room);
        return "redirect:/rooms";
    }
    
    @PostMapping("update")
    public String update(@ModelAttribute("room") Room room) {
        service.update(room);
        return "redirect:/rooms";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Room room = service.getById(id);
        model.addAttribute("room", room);
        return "room/edit";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        service.deleteById(id);
        return "redirect:/rooms";
    }
}