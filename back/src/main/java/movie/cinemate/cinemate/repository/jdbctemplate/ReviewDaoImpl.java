package movie.cinemate.cinemate.repository.jdbctemplate;

import lombok.extern.slf4j.Slf4j;
import movie.cinemate.cinemate.dto.review.ReviewRequestDto;
import movie.cinemate.cinemate.dto.review.ReviewResponseDto;
import movie.cinemate.cinemate.entity.movie.Review;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
public class ReviewDaoImpl {
    NamedParameterJdbcTemplate template;

    public ReviewDaoImpl(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * TODO : 영화 리뷰 작성
     */
    public Long addReview(ReviewRequestDto dto, String userId) {
        String sql = "insert into review (movie_id, user_id, content, created_at, rate) " +
                "values (:movieId, :userId, :content, :createdAt, :rate)";

        Review review = Review.builder()
                              .movieId(dto.getMovieId())
                              .userId(userId)
                              .content(dto.getContent())
                              .rate(dto.getRate())
                              .createdAt(LocalDateTime.now())
                              .build();

        SqlParameterSource param = new BeanPropertySqlParameterSource(review);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);

        return keyHolder.getKey()
                        .longValue();
    }

    /**
     * TODO : 영화 리뷰 수정
     */
    public Long updateReview(ReviewRequestDto dto, Long reviewId) {
        String sql = "update review " +
                "set content=:content, updated_at=:updatedAt " +
                "where review_id=:reviewId";

        SqlParameterSource param = new BeanPropertySqlParameterSource(
                Review.builder()
                      .content(dto.getContent())
                      .rate(dto.getRate()));

        template.update(sql, param);
        return reviewId;
    }

    /**
     * TODO : 개별 영화 리뷰 조회(개발용)
     */
    public Optional<ReviewResponseDto> findReviewById(Long reviewId) {

        try {
            return Optional.ofNullable(
                    template.queryForObject(
                            "select * from review where review_id = :reviewId",
                            Map.of("reviewId", reviewId),
                            new BeanPropertyRowMapper<>(ReviewResponseDto.class)
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * TODO : 해당 영화의 모든 리뷰 조회
     */
    public List<ReviewResponseDto> findReviewByMovieId(Long movieId) {
        return template.query("select * " +
                "from review " +
                "where review.movie_id = :movieId",
                Map.of("movidId", movieId),
                new BeanPropertyRowMapper<>(ReviewResponseDto.class));
    }


    /**
     * TODO : 영화 리뷰 삭제
     */
    public void delete(Long reviewId) {
        template.update("delete from review where review_id=:reviewId",
                new MapSqlParameterSource()
                        .addValue("reviewId", reviewId));

    }
}
