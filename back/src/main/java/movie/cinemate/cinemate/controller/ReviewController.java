package movie.cinemate.cinemate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import movie.cinemate.cinemate.dto.review.ReviewRequestDto;
import movie.cinemate.cinemate.dto.review.ReviewResource;
import movie.cinemate.cinemate.entity.member.Member;
import movie.cinemate.cinemate.entity.movie.Review;
import movie.cinemate.cinemate.repository.ReviewDao;
import movie.cinemate.cinemate.service.ReviewService;
import movie.cinemate.cinemate.service.impl.ReviewServiceImpl;
import movie.cinemate.cinemate.utils.argumentresolver.Login;
import movie.cinemate.cinemate.utils.session.SessionData;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RequestMapping("/api/v1/movies/{movieId}/reviews")
@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewDao reviewDao;

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
    @PostMapping()
    public ResponseEntity write(
            @ModelAttribute ReviewRequestDto dto,
//            @SessionAttribute(name = "loginUser", required = false) String userId,
            @PathVariable("movieId") Long movieId,
            @Login Member loginMember
            ) {
        Long reviewId = reviewService.write(dto, loginMember);
        WebMvcLinkBuilder linkBuilder = linkTo(methodOn(ReviewController.class, movieId).find(reviewId));
        URI uri = linkBuilder.toUri();
        log.info("uri = {}", uri);

        ReviewResource reviewResource = new ReviewResource(reviewId);
        reviewResource.add(linkTo(ReviewController.class, movieId).withSelfRel());
        return ResponseEntity.created(uri).body(reviewResource);
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
    public ResponseEntity<Review> find(@PathVariable Long reviewId) {
        System.out.println("asdf");
        return new ResponseEntity<>(reviewDao.findByReviewId(reviewId).get(), HttpStatus.OK);
    }

    /**
     * TODO : 리뷰 좋아요 누르기
     */
    @PostMapping("/{reviewId}/like")
    public void likeReview(@PathVariable Long reviewId) {

    }
}
