package movie.cinemate.cinemate.repository;

import movie.cinemate.cinemate.entity.movie.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {
    Long add(Review review);
    Long updateReview(Review review);
    Optional<Review> findByReviewId(Long reviewId);
    List<Review> findAllByMovieId(Long movieId);
    void delete(Long reviewId);
    Optional<String> findUserIdFromReview(Long reviewId);
}
