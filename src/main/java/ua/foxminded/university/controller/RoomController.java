package ua.foxminded.university.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.sun.org.apache.xpath.internal.operations.Mod;
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

import ua.foxminded.university.dto.RoomDTO;
import ua.foxminded.university.model.Room;
import ua.foxminded.university.service.RoomService;

import javax.validation.Valid;

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
}