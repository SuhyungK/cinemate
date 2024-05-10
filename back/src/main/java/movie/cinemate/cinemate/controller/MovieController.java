package movie.cinemate.cinemate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import movie.cinemate.cinemate.dto.movie.MovieResponseDto;
import movie.cinemate.cinemate.dto.movie.MovieDto;
import movie.cinemate.cinemate.entity.member.Member;
import movie.cinemate.cinemate.repository.jdbctemplate.MovieDaoImpl;
import movie.cinemate.cinemate.service.MovieService;
import movie.cinemate.cinemate.utils.argumentresolver.Login;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    /**
     * 전체 영화 조회 페이징
     */
    @ResponseBody
    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
//            @SessionAttribute(name = "loginUser") String userId
//            @Login Member loginMember
            // TODO : @SessionCheck 어노테이션 작성해서 userId 가져올 수 있도록 하기
            // TODO : ArgumentResolver 공부 하기
    ) {
        log.info("page={}", page);
//        log.info("loginMember={}", loginMember);
//        log.info("userId={}", userId);

        // TODO : 메인 화면 진입시 회원인 경우 (userId 있는 경우) 프로필 사진, 비회원인 경우 로그인 버튼
        return ResponseEntity.ok(movieService.findAll(page, size));
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
    public ResponseEntity getDetail(@PathVariable Long movieId) {
        return ResponseEntity.ok(movieService.findById(movieId));
    }
}
