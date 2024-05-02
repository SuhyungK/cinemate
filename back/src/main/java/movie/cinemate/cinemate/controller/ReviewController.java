package movie.cinemate.cinemate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import movie.cinemate.cinemate.dto.review.ReviewRequestDto;
import movie.cinemate.cinemate.entity.member.Member;
import movie.cinemate.cinemate.entity.movie.Review;
import movie.cinemate.cinemate.repository.jdbctemplate.ReviewDaoImpl;
import movie.cinemate.cinemate.service.impl.ReviewServiceImpl;
import movie.cinemate.cinemate.utils.argumentresolver.Login;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequestMapping("/api/v1/movies/{movieId}/reviews")
@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewServiceImpl reviewService;
    private final ReviewDaoImpl reviewDao;

    /**
     * 해당 영화 전체 리뷰 조회
     */
    @GetMapping
    public ResponseEntity<List<Review>> findAllMovieReview(@PathVariable Long movieId) {
        return new ResponseEntity<>(reviewDao.findAllByMovieId(movieId), HttpStatus.OK);
    }

    /**
     * 리뷰 작성
     */
    @PostMapping
    public void writeReview(
            @ModelAttribute ReviewRequestDto dto,
//            @SessionAttribute(name = "loginUser", required = false) String userId
            @Login Member loginMember
            ) {
//        reviewService.writeReview(dto, userId);
        log.info("loginMember: {}", loginMember);

        reviewDao.add(Review.builder()
                            .createdAt(LocalDateTime.now())
                            .rate(3.5F)
                            .memberId("23b300a8fbb211ee945a5a8cafcae624")
                            .movieId(693134L)
                            .content("기가 막힌다")
                            .build());
    }

    /**
     * TODO : 리뷰 수정
     */
    @PutMapping("/{reviewId}")
    public void editReview(@PathVariable Long reviewId) {

    }

    /**
     * TODO : 리뷰 삭제
     */
    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId) {

    }

    /**
     * TODO : 개별 리뷰 조회 (개발용)
     */
    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> findReview(@PathVariable Long reviewId) {
        return new ResponseEntity<>(reviewDao.findByReviewId(reviewId).get(), HttpStatus.OK);
    }

    /**
     * TODO : 리뷰 좋아요 누르기
     */
    @PostMapping("/{reviewId}/like")
    public void likeReview(@PathVariable Long reviewId) {

    }
}
