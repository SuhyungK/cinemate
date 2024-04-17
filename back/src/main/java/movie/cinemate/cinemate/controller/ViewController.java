package movie.cinemate.cinemate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/api/v1")
public class ViewController {

    @GetMapping
    public String movieMain() {
        log.info("MainController");
        return "redirect:/";
    }
}
