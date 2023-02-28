package ua.foxminded.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/university")
    public String welcome() {
        return "main_page";
    }
}
