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

import ua.foxminded.university.dto.LectureDTO;
import ua.foxminded.university.model.Lecture;
import ua.foxminded.university.service.LectureService;

import javax.validation.Valid;

@Controller
@RequestMapping("/lectures")
public class LectureController {
    private LectureService service;
    private ModelMapper modelMapper;

    @Autowired
    public LectureController(LectureService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String list(Model model) {
        List<Lecture> lectures = service.getAll();
        List<LectureDTO> lectureDTOList = lectures.stream().map(lecture -> modelMapper.map(lecture, LectureDTO.class)).collect(Collectors.toList());
        model.addAttribute("lectures", lectureDTOList);
        return "lecture/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("lecture", new LectureDTO());
        return "lecture/add";
    }

    @PostMapping("save")
    public String save(@Valid @ModelAttribute("lecture") LectureDTO lectureDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "lecture/add";
        }
        Lecture lecture = modelMapper.map(lectureDTO, Lecture.class);
        service.save(lecture);
        return "redirect:/lectures";
    }

    @PatchMapping("update")
    public String update(@Valid @ModelAttribute("lecture") LectureDTO lectureDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "lecture/edit";
        }
        Lecture lecture = modelMapper.map(lectureDTO, Lecture.class);
        service.save(lecture);
        return "redirect:/lectures";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Lecture lecture = service.getById(id);
        LectureDTO lectureDTO = modelMapper.map(lecture, LectureDTO.class);
        model.addAttribute("lecture", lectureDTO);
        return "lecture/edit";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        service.deleteById(id);
        return "redirect:/lectures";
    }
}
