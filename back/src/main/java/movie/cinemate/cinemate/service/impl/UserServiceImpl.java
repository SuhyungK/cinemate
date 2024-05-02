package movie.cinemate.cinemate.service.impl;

import lombok.RequiredArgsConstructor;
import movie.cinemate.cinemate.dto.member.MemberDto;
import movie.cinemate.cinemate.dto.member.MemberResponseDto;
import movie.cinemate.cinemate.entity.member.Member;
import movie.cinemate.cinemate.handler.exception.CustomUserException;
import movie.cinemate.cinemate.repository.jdbctemplate.MemberDaoImpl;
import movie.cinemate.cinemate.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final MemberDaoImpl memberDao;
    @Autowired
    private final ModelMapper modelMapper;

    public String join(MemberDto joinDto) {
        if (!checkDuplicatedUser(joinDto.getLoginId())) { // 중복 아이디 있을 시 가입 불가
            throw new CustomUserException("중복 가입입니다", 1);
        }
        return memberDao.save(mapToEntity(joinDto));
    }

    public Member login(MemberDto loginDto) {
        String memberId = memberDao.loginCheck(modelMapper.map(loginDto, Member.class));
        if (memberId == null) {
            return null;
        }
        return memberDao.findByMemberId(memberId);
    }

    private boolean checkDuplicatedUser(String id) {
        // 중복 아이디 확인 여부
        return memberDao.findById(id) == null;
    }

    private Member mapToEntity(MemberDto memberDto) {
        return Member.builder()
                     .loginId(memberDto.getLoginId())
                     .password(memberDto.getPassword())
                     .joinedAt(LocalDateTime.now())
                     .build();
    }

    private MemberDto mapToDto(Member member) {
        return MemberDto.builder()
                        .loginId(member.getLoginId())
                        .password(member.getPassword())
                        .build();
    }

    private MemberResponseDto mapToResponseDto(Member member) {
        return MemberResponseDto.builder()
                                .loginId(member.getLoginId())
                                .nickname(member.getNickname())
                                .build();
    }
}
