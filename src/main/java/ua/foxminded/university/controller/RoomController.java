package ua.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.university.dto.RoomDTO;
import ua.foxminded.university.service.RoomService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {
    private RoomService service;

    @Autowired
    public RoomController(RoomService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        List<RoomDTO> roomDTOList = service.getAll();
        model.addAttribute("rooms", roomDTOList);
        return "room/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("room", new RoomDTO());
        return "room/add";
    }

    @PostMapping("save")
    public String save(@Valid @ModelAttribute("room") RoomDTO roomDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "room/add";
        }
        service.save(roomDTO);
        return "redirect:/rooms";
    }

    @PatchMapping("update")
    public String update(@Valid @ModelAttribute("room") RoomDTO roomDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "room/edit";
        }
        service.save(roomDTO);
        return "redirect:/rooms";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        RoomDTO roomDTO = service.getById(id);
        model.addAttribute("room", roomDTO);
        return "room/edit";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        service.deleteById(id);
        return "redirect:/rooms";
    }
}//