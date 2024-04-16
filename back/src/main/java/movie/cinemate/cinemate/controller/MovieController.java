package movie.cinemate.cinemate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import movie.cinemate.cinemate.dto.movie.MovieDetailDto;
import movie.cinemate.cinemate.dto.movie.MovieDto;
import movie.cinemate.cinemate.repository.jdbctemplate.MovieDaoImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieDaoImpl movieRepository;

    /**
     * 전체 영화 조회 (개발용)
     */
    @ResponseBody
    @GetMapping("/movies")
    public ResponseEntity<List<MovieDto>> movieMain() {
        log.info("영화 메인 화면");
        return new ResponseEntity<>(movieRepository.findAll(1), HttpStatus.OK);
    }

    /**
     * 전체 영화 조회 페이징
     */
    @GetMapping
    public ResponseEntity<List<MovieDto>> movieByPage(@RequestParam int page) {
        log.info("page={}", page);
        return new ResponseEntity<>(movieRepository.findAll(page), HttpStatus.OK);
    }

    /**
     * 키워드로 영화 제목 검색
     */
    @ResponseBody
    @GetMapping("/search")
    public String search(@RequestParam("q") String q) {
        return null;
    }

    /**
     * 개별 영화 조회
     */
    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDetailDto> movieDetail(@PathVariable Long movieId) {
        log.info("movieId : {}", movieId);
        return new ResponseEntity<>(movieRepository.getMovieDetail(movieId), HttpStatus.OK);
    }
}
