package movie.cinemate.cinemate.repository.jdbctemplate;

import movie.cinemate.cinemate.dto.user.JoinDto;
import movie.cinemate.cinemate.entity.user.User;
import movie.cinemate.cinemate.repository.UserDao;
import movie.cinemate.cinemate.utils.UUIDUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Repository
public class UserDaoImpl implements UserDao {

    NamedParameterJdbcTemplate template;

    public UserDaoImpl(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * 유저 저장 (가입)
     */
    public String save(JoinDto dto) {
        String query = "insert into user (user_id, id, password, joined_at) " +
                "values (:user_id, :id, :password, :joinedAt)";

        String userId = UUIDUtil.createUUID(UUID.randomUUID()
                                .toString());
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("id", dto.getId())
                .addValue("password", dto.getPassword())
                .addValue("joinedAt", LocalDateTime.now());

//        template.update(query, param);
        return userId;
    }

    /**
     * 유저 테이블의 PK인 userId로 찾기
     */
    public User findByUserId(Long userId) {
        return null;
    }

    /**
     * 유저가 가입할 때 쓴 id로 찾기(PK 아님!)
     */
    public User findById(String id) {
        return template.queryForObject(
                "select * from user where id = :id",
                Map.of("id", id),
                BeanPropertyRowMapper.newInstance(User.class));
    }


}
