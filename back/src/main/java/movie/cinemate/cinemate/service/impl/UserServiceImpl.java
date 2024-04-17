package movie.cinemate.cinemate.service.impl;

import lombok.RequiredArgsConstructor;
import movie.cinemate.cinemate.dto.user.UserDto;
import movie.cinemate.cinemate.handler.exception.CustomUserException;
import movie.cinemate.cinemate.repository.jdbctemplate.UserDaoImpl;
import movie.cinemate.cinemate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserDaoImpl userDao;

    public String join(UserDto joinDto) {
        if (userDao.countById(joinDto.getId()) == 1) {
            // 가입한 사람이 있음(1명) -> 가입 불가
            throw new CustomUserException("중복 가입입니다", 1);
        }
        return userDao.save(joinDto);
    }

    public String login(UserDto loginDto) {
        return userDao.loginCheck(loginDto);
    }
}
