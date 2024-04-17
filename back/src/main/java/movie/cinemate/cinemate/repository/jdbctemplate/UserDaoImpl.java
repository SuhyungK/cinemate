package movie.cinemate.cinemate.repository.jdbctemplate;

import lombok.extern.slf4j.Slf4j;
import movie.cinemate.cinemate.dto.user.UserDto;
import movie.cinemate.cinemate.entity.user.User;
import movie.cinemate.cinemate.repository.UserDao;
import movie.cinemate.cinemate.utils.UUIDUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Repository
public class UserDaoImpl implements UserDao {

    NamedParameterJdbcTemplate template;

    public UserDaoImpl(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * 유저 저장 (가입)
     */
    public String save(UserDto dto) {
        String query = "insert into user (user_id, id, password, joined_at) " +
                "values (:user_id, :id, :password, :joinedAt)";

        String userId = UUIDUtil.createUUID(UUID.randomUUID()
                                                .toString());
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("id", dto.getId())
                .addValue("password", dto.getPassword())
                .addValue("joinedAt", LocalDateTime.now());

        template.update(query, param);
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
    public Integer countById(String id) {
        // TODO : 가입된 아이디가 있다면 1 반환
        // TODO : 가입된 아이디가 없다면 0 반환
        return Objects.requireNonNull(template.queryForObject(
                "select count(*) as count from user where id = :id",
                Map.of("id", id),
                Integer.class));
    }

    /**
     * 로그인
     * TODO : 아이디 비밀번호가 모두 일치하는 유저를 찾으면 그 유저의 userId를 넘겨준다
     */
    public String loginCheck(UserDto dto) {
        log.info("dao의 dto {}", dto);
        List<User> users = template.query(
                "select user_id from user where id = :id and password = :password",
                new BeanPropertySqlParameterSource(dto),
                new BeanPropertyRowMapper<>(User.class));

        if (users.size() == 1) {
            // 1명만 검색되면 아이디 반환
            return users.get(0)
                        .getUserId();
        }
        // 2명 이상이거나(이건 서버 문제) 없으면 로그인 정보가 없는 거니까 null
        return null;
    }
}
