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
@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /**
     * 모든 영화 정보 (개발용)
     */
    @ResponseBody
    @GetMapping("/")
    public ResponseEntity<List<MovieDTO>> movieMain() {
        log.info("영화 메인 화면");
        return new ResponseEntity<>(movieRepository.findAll(), HttpStatus.OK);
    }

    /**
     * 키워드로 영화 제목 검색
     */
    @ResponseBody
    @GetMapping("/search")
    public ResponseEntity<List<MovieDTO>> search(@RequestParam("keyword") String keyword) {
        return new ResponseEntity<>(movieRepository.findByKeyword(keyword), HttpStatus.OK);
    }
}
