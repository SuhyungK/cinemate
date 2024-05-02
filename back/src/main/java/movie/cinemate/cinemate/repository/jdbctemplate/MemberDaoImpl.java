package movie.cinemate.cinemate.repository.jdbctemplate;

import lombok.extern.slf4j.Slf4j;
import movie.cinemate.cinemate.entity.member.Member;
import movie.cinemate.cinemate.repository.MemberDao;
import movie.cinemate.cinemate.utils.UUIDUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Repository
public class MemberDaoImpl implements MemberDao {

    NamedParameterJdbcTemplate template;

    public MemberDaoImpl(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * 유저 저장 (가입)
     */
    public String save(Member member) {
        String query = "insert into MEMBER (member_id, login_id, password, joined_at) " +
                "values (:memberId, :loginId, :password, :joinedAt)";

        String memberId = UUIDUtil.createUUID(UUID.randomUUID()
                                                .toString());
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("loginId", member.getLoginId())
                .addValue("password", member.getPassword())
                .addValue("joinedAt", member.getJoinedAt());

        template.update(query, param);
        return memberId;
    }

    /**
     * 유저 테이블의 PK인 userId로 찾기
     */
    public Member findByMemberId(String memberId) {
        return template.queryForObject(
                "select * from MEMBER where member_id = :memberId",
                Map.of("memberId", memberId),
                new BeanPropertyRowMapper<>(Member.class));
    }

    /**
     * 유저가 가입할 때 쓴 id로 찾기(PK 아님!)
     */
    public Member findById(String loginId) {

        List<Member> members = template.query(
                "select * from MEMBER where login_id = :loginId",
                Map.of("loginId", loginId),
                new BeanPropertyRowMapper<>(Member.class));

        if (members.size() == 1) {
            return members.getFirst();
        }
        return null;
    }

    /**
     * 로그인
     * TODO : 아이디 비밀번호가 모두 일치하는 유저를 찾으면 그 유저의 userId를 넘겨준다
     */
    public String loginCheck(Member member) {
        List<Member> members = template.query(
                "select member_id from MEMBER where login_id = :loginId and password = :password",
                new BeanPropertySqlParameterSource(member),
                new BeanPropertyRowMapper<>(Member.class));

        if (members.size() == 1) {
            // 1명만 검색되면 아이디 반환
            return members.get(0)
                          .getMemberId();
        }
        // 2명 이상이거나(이건 서버 문제) 없으면 로그인 정보가 없는 거니까 null
        return null;
    }
}
