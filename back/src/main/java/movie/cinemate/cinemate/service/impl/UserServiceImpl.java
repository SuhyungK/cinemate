package movie.cinemate.cinemate.service.impl;

import lombok.RequiredArgsConstructor;
import movie.cinemate.cinemate.dto.user.UserDto;
import movie.cinemate.cinemate.dto.user.UserResponseDto;
import movie.cinemate.cinemate.entity.user.User;
import movie.cinemate.cinemate.handler.exception.CustomUserException;
import movie.cinemate.cinemate.repository.jdbctemplate.UserDaoImpl;
import movie.cinemate.cinemate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserDaoImpl userDao;

    public String join(UserDto joinDto) {
        if (checkDuplicatedUser(joinDto.getId())) { // 중복 아이디 있을 시 가입 불가
            throw new CustomUserException("중복 가입입니다", 1);
        }
        return userDao.save(mapToEntity(joinDto));
    }

    public String login(UserDto loginDto) {
        return userDao.loginCheck(mapToEntity(loginDto));
    }

    private boolean checkDuplicatedUser(String id) {
        // 중복 아이디 확인 여부
        return userDao.findById(id) == null;
    }

    private User mapToEntity(UserDto userDto) {
        return User.builder()
                   .id(userDto.getId())
                   .password(userDto.getPassword())
                   .joinedAt(LocalDateTime.now())
                   .build();
    }

    private UserDto mapToDto(User user) {
        return UserDto.builder()
                      .id(user.getId())
                      .password(user.getPassword())
                      .build();
    }

    private UserResponseDto mapToResponseDto(User user) {
        return UserResponseDto.builder()
                              .id(user.getId())
                              .nickname(user.getNickname())
                              .build();
    }
}
