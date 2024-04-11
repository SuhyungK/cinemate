package movie.cinemate.cinemate.controller;

import lombok.extern.slf4j.Slf4j;
import movie.cinemate.cinemate.domain.MovieDTO;
import movie.cinemate.cinemate.repository.MovieRepository;
import movie.cinemate.cinemate.repository.jdbc.MovieJdbcRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/api/v1")
@RestController
public class MainController {

    private final MovieRepository movieRepository;

    public MainController(MovieJdbcRepository movieJdbcRepository) {
        this.movieRepository = movieJdbcRepository;
    }

    @GetMapping("")
    public String home() {
        System.out.println("왔다");
        return "Hello World";
    }

    @ResponseBody
    @GetMapping("/movies")
    public ResponseEntity<List<MovieDTO>> movieMain() {
        log.info("영화 메인");
        return new ResponseEntity<>(movieRepository.findAll(), HttpStatus.OK);
    }
}
