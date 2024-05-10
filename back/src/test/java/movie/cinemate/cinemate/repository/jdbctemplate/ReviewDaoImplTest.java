package movie.cinemate.cinemate.repository.jdbctemplate;

import movie.cinemate.cinemate.entity.member.Member;
import movie.cinemate.cinemate.entity.movie.Review;
import movie.cinemate.cinemate.repository.ReviewDao;
import movie.cinemate.cinemate.util.Data;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
class ReviewDaoImplTest {

    @Autowired
    ReviewDao reviewDao;

    @Autowired
    MemberDaoImpl memberDao;

    Review review;
    String memberId = Data.MEMBER_ID;
    Long movieId = Data.MOVIE_ID;

    @BeforeEach
    void before() {
        review = Review.builder()
                       .movieId(movieId)
                       .memberId(memberId)
                       .content("내용")
                       .rate(3.5F)
                       .createdAt(LocalDateTime.now())
                       .build();
    }

    @Test
    void ReviewDao_add_ReturnReviewIdLong() {
        Long addReviewId = reviewDao.add(review);

        assertThat(addReviewId).isNotNull();
    }

    @Test
    void ReviewDao_updateReview_ReturnReviewIdLong() {
        // Arrange
        Long reviewId = reviewDao.add(review);
        review.setReviewId(reviewId);

        String updateContent = "업데이트 할 내용";
        LocalDateTime updateDate = LocalDateTime.of(2024, 4, 19, 3, 8);

        review.setContent(updateContent);
        review.setUpdatedAt(updateDate);

        // Act
        Long updatedReviewId = reviewDao.updateReview(review);
        Review review = reviewDao.findByReviewId(reviewId).orElse(new Review());

        // Assert
        assertThat(updatedReviewId).isEqualTo(reviewId);
        assertThat(review.getReviewId()).isEqualTo(reviewId);
        assertThat(review.getUpdatedAt()).isEqualTo(updateDate);
        assertThat(review.getContent()).isEqualTo(updateContent);
    }

    @Test
    void ReviewDao_findByReviewId_ReturnOneReview() {
        Long reviewId = reviewDao.add(review);

        Optional<Review> findReview = reviewDao.findByReviewId(reviewId);

        System.out.println(findReview.get().getReviewId());
        assertThat(findReview).isNotEmpty();
        assertThat(findReview.get().getMember()).isNotNull();
    }

    @Test
    void ReviewDao_findByReviewIdIfReviewIdNotExist_ReturnNull() {
        Optional<Review> findReview = reviewDao.findByReviewId(0L);

        assertThat(findReview).isEmpty();
    }

    @Test
    void ReviewDao_findAllByMovieId_ReturnReviewList() {
        // Arrange : 해당 영화에 대한 리뷰 3개 저장
        int repeatTime = 3;
        for (int i = 0; i < 3; i++) {
            reviewDao.add(review);
        }

        // Act
        List<Review> reviewList = reviewDao.findAllByMovieId(movieId);

        // Assert
        assertThat(reviewList).isNotEmpty();
        assertThat(reviewList.size()).isEqualTo(repeatTime);
    }

    @Test
    void ReviewDao_delete() {
        Long newReviewId = reviewDao.add(review);

        Assertions.assertAll(
                () -> reviewDao.delete(newReviewId)
        );
    }
}
