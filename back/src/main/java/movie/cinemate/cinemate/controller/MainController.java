package movie.cinemate.cinemate.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:5174")
@RequestMapping("/api/v1")
@RestController
public class MainController {

    @GetMapping("")
    public String home() {
        System.out.println("왔다");
        return "Hello World";
    }
}
