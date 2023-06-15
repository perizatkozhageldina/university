package ua.foxminded.university.controller;

import java.util.List;
import java.util.Optional;

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

import ua.foxminded.university.dto.CourseDTO;
import ua.foxminded.university.model.Course;
import ua.foxminded.university.service.CourseService;

import javax.validation.Valid;

@Controller
@RequestMapping("/courses")
public class CourseController {
	private CourseService service;

	@Autowired
	public CourseController(CourseService service) {
		this.service = service;
    }

	@GetMapping
	public String list(Model model) {
		List<CourseDTO> courseDTOList = service.getAll();
		model.addAttribute("courses", courseDTOList);
		return "course/index";
	}

	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("course", new CourseDTO());
		return "course/add";
	}

	@PostMapping("save")
	public String save(@Valid @ModelAttribute("course") CourseDTO courseDTO, BindingResult result) {
		if (result.hasErrors()) {
			return "course/add";
		}
		service.save(courseDTO);
		return "redirect:/courses";
	}

	@PatchMapping("update")
	public String update(@Valid @ModelAttribute("course") CourseDTO courseDTO, BindingResult result) {
		if (result.hasErrors()) {
			return "course/edit";
		}
		service.save(courseDTO);
		return "redirect:/courses";
	}

	@GetMapping("/{id}/edit")
	public String edit(@PathVariable("id") Long id, Model model) {
		CourseDTO courseDTO = service.getById(id);
		model.addAttribute("course", courseDTO);
		return "course/edit";
	}

	@DeleteMapping("/{id}/delete")
	public String delete(@PathVariable("id") Long id) {
		service.deleteById(id);
		return "redirect:/courses";
	}
}