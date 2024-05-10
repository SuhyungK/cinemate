package movie.cinemate.cinemate.repository.jdbctemplate;

import movie.cinemate.cinemate.entity.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class MemberDaoImplTest {
    @Autowired
    MemberDaoImpl memberDao;

    Member member;
    String id = "mockId";
    String password = "mockPassword";

    @BeforeEach
    void before() {
        member = Member.builder()
                       .loginId(id)
                       .password(password)
                       .joinedAt(LocalDateTime.now())
                       .build();
    }

    @DisplayName("유저 정상 가입 테스트")
    @Test
    void MemberDao_Save_Return32LengthString() {
        String savedMemberId = memberDao.save(member);

        assertThat(savedMemberId).isNotNull();
        assertThat(savedMemberId.length()).isEqualTo(32);
    }

    @DisplayName("PK 검색 시 User 반환")
    @Test
    void MemberDao_findByMemberId_ReturnMember() {
        String savedMemberId = memberDao.save(member);

        Member findMember = memberDao.findByMemberId(savedMemberId);

        assertThat(findMember).isNotNull();
        assertThat(findMember.getLoginId()).isEqualTo(id);
        assertThat(findMember.getPassword()).isEqualTo(password);
        assertThat(findMember).isInstanceOf(Member.class);
    }

    @DisplayName("id로 유저 찾아서 User 반환하기")
    @Test
    void MemberDao_findById_ReturnMember() {
        memberDao.save(member);

        Member findMember = memberDao.findById(id);

        assertThat(findMember).isNotNull();
        assertThat(findMember.getLoginId()).isEqualTo(id);
        assertThat(findMember.getPassword()).isEqualTo(password);
        assertThat(findMember).isInstanceOf(Member.class);
    }

    @DisplayName("중복된 id가 존재할 시 null 반환")
    @Test
    void MemberDao_findById_ifSameIdMemberExists_ReturnNull() {
        Member newMember = Member.builder()
                                 .loginId(id)
                                 .password(password)
                                 .joinedAt(LocalDateTime.now())
                                 .build();

        memberDao.save(member);
        memberDao.save(newMember); // id가 중복되는 새로운 유저

        Member findMember = memberDao.findById(id);

        assertThat(findMember).isNull();
    }

    @DisplayName("id와 password가 일치하는 유저가 로그인 하면 userId 반환")
    @Test
    void MemberDao_loginCheck_ReturnMemberIdString() {
        String savedMemberId = memberDao.save(member);
        Member loginMember = memberDao.findByMemberId(savedMemberId);

        String loginMemberId = memberDao.loginCheck(loginMember);

        assertThat(loginMemberId).isNotNull();
        assertThat(loginMemberId).isEqualTo(savedMemberId);
    }
}