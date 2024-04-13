package movie.cinemate.cinemate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import movie.cinemate.cinemate.dto.MovieDetailDto;
import movie.cinemate.cinemate.dto.MovieDto;
import movie.cinemate.cinemate.repository.jdbctemplate.MovieRepositoryImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieRepositoryImpl movieRepository;

    /**
     * 전체 영화 조회 (개발용)
     */
    @ResponseBody
    @GetMapping
    public ResponseEntity<List<MovieDto>> movieMain() {
        log.info("영화 메인 화면");
        return new ResponseEntity<>(movieRepository.findAll(1), HttpStatus.OK);
    }

    /**
     * 전체 영화 조회 페이징
     */
//    @GetMapping
//    public void moviePage(@RequestParam int page) {
//
//    }

    /**
     * 키워드로 영화 제목 검색
     */
    @ResponseBody
    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword) {
        return null;
    }

    /**
     * 개별 영화 조회
     */
    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDetailDto> movieDetail(@PathVariable Long movieId) {
//        log.info("movieId : {}", movieId);
        return new ResponseEntity<>(movieRepository.getMovieDetail(movieId), HttpStatus.OK);
    }
}
