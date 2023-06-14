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
    private ModelMapper modelMapper;

    @Autowired
    public RoomController(RoomService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String list(Model model) {
        List<Room> rooms = service.getAll();
        List<RoomDTO> roomDTOList = rooms.stream().map(room -> modelMapper.map(room, RoomDTO.class)).collect(Collectors.toList());
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
        Room room = modelMapper.map(roomDTO, Room.class);
        service.save(room);
        return "redirect:/rooms";
    }

    @PatchMapping("update")
    public String update(@Valid @ModelAttribute("room") RoomDTO roomDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "room/edit";
        }
        Room room = modelMapper.map(roomDTO, Room.class);
        service.save(room);
        return "redirect:/rooms";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Room room = service.getById(id);
        RoomDTO roomDTO = modelMapper.map(room, RoomDTO.class);
        model.addAttribute("room", roomDTO);
        return "room/edit";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        service.deleteById(id);
        return "redirect:/rooms";
    }
}