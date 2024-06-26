package movie.cinemate.cinemate.repository.jdbctemplate;

import lombok.extern.slf4j.Slf4j;
import movie.cinemate.cinemate.entity.movie.Review;
import movie.cinemate.cinemate.repository.ReviewDao;
import movie.cinemate.cinemate.utils.NestedRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class ReviewDaoImpl implements ReviewDao {
    NamedParameterJdbcTemplate template;

    public ReviewDaoImpl(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * TODO : 영화 리뷰 작성
     */
    public Long add(Review review) {
        String sql = "insert into review (movie_id, member_id, content, created_at, rate) " +
                "values (:movieId, :memberId, :content, :createdAt, :rate)";

//        review.setCreatedAt(LocalDateTime.now());
        SqlParameterSource param = new BeanPropertySqlParameterSource(review);
        KeyHolder keyHolder = new GeneratedKeyHolder();
//        System.out.println(keyHolder.getKey());
//        log.info("keyHolder : {}", keyHolder.getKey().longValue());
        template.update(sql, param, keyHolder);

        return keyHolder.getKey().longValue();
    }

    /**
     * TODO : 영화 리뷰 수정
     */
    public Long updateReview(Review review) {
        Long reviewId = review.getReviewId();
        String sql = "update review " +
                "set content=:content, updated_at=:updatedAt " +
                "where review_id=:reviewId";

        SqlParameterSource param = new BeanPropertySqlParameterSource(review);

        template.update(sql, param);
        return reviewId;
    }

    /**
     * TODO : 개별 영화 리뷰 조회(개발용)
     */
    public Optional<Review> findByReviewId(Long reviewId) {
        try {
            return Optional.ofNullable(template.queryForObject(
                    "select r.movie_id, " +
                            "r.review_id, " +
                            "member.login_id As `member.login_id`, " +
                            "member.nickname AS `member.nickname`, " +
                            "r.content, " +
                            "r.rate, " +
                            "r.created_at, " +
                            "r.updated_at " +
                    "from review r " +
                    "join member " +
                    "on r.member_id = member.member_id " +
                    "where r.review_id = :reviewId", Map.of("reviewId", reviewId), new NestedRowMapper<>(Review.class)));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * TODO : 해당 영화의 모든 리뷰 조회
     */
    public List<Review> findAllByMovieId(Long movieId) {
        return template.query(
                "select r.movie_id , " +
                        "r.review_id , " +
                        "r.content, r.rate, " +
                        "r.created_at , " +
                        "u.login_id as `member.login_id`, " +
                        "u.nickname as `member.nickname` from review r join member u on r.member_id = u.member_id where r.movie_id = :movieId",
                Map.of("movieId", movieId),
                new NestedRowMapper<>(Review.class));
    }


    /**
     * TODO : 영화 리뷰 삭제
     */
    public void delete(Long reviewId) {
        template.update("delete from review where review_id=:reviewId",
                new MapSqlParameterSource()
                        .addValue("reviewId", reviewId));

    }

    /**
     * TODO : 리뷰 작성자의 userId
     */
    public Optional<String> findUserIdFromReview(Long reviewId) {
        try {
            return Optional.ofNullable(template.queryForObject(
                    "select member_id from review where review_id = :reviewId",
                    Map.of("reviewId", reviewId),
                    String.class
            ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}