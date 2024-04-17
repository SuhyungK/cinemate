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
    @GetMapping("/movies")
    public ResponseEntity<List<MovieDto>> movieMain() {
//        log.info("영화 메인 화면");
        return new ResponseEntity<>(movieRepository.findAll(1), HttpStatus.OK);
    }

    /**
     * 전체 영화 조회 페이징
     */
    @ResponseBody
    @GetMapping
    public ResponseEntity<List<MovieDto>> movieByPage(
            @RequestParam int page,
            @SessionAttribute(name = "loginUser", required = false) String userId
            // TODO : @SessionCheck 어노테이션 작성해서 userId 가져올 수 있도록 하기
            // TODO : ArgumentResolver 공부 하기
    ) {
        log.info("page={}", page);
        log.info("loginUser={}", userId);

        // TODO : 메인 화면 진입시 회원인 경우 (userId 있는 경우) 프로필 사진, 비회원인 경우 로그인 버튼
        return new ResponseEntity<>(movieRepository.findAll(page), HttpStatus.OK);
    }

    /**
     * 키워드로 영화 제목 검색
     */
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
