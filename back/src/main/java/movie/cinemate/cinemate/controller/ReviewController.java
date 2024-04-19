package movie.cinemate.cinemate.controller;

import lombok.RequiredArgsConstructor;
import movie.cinemate.cinemate.dto.review.ReviewRequestDto;
import movie.cinemate.cinemate.entity.movie.Review;
import movie.cinemate.cinemate.repository.jdbctemplate.ReviewDaoImpl;
import movie.cinemate.cinemate.service.impl.ReviewServiceImpl;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/movie/review")
@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewServiceImpl reviewService;
    private final ReviewDaoImpl reviewDao;

    /**
     * 리뷰 작성 API
     */
    @PostMapping
    public void writeReview(
            @ModelAttribute ReviewRequestDto dto,
            @SessionAttribute(name = "loginUser", required = false) String userId
    ) {
        reviewService.writeReview(dto, userId);
    }

    /**
     * TODO : 리뷰 수정
     */
    @PostMapping("/edit")
    public void editReview() {

    }

    /**
     * TODO : 개별 리뷰 조회 (개발용)
     */
    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> findReview(@PathVariable Long reviewId) {
        return new ResponseEntity<>(reviewDao.findByReviewId(reviewId).get(), HttpStatus.OK);
    }

    @GetMapping("/reviews/{movieId}")
    public ResponseEntity<List<Review>> findAllMovieReview(@PathVariable Long movieId) {
        return new ResponseEntity<>(reviewDao.findAllByMovieId(movieId), HttpStatus.OK);
    }
}
