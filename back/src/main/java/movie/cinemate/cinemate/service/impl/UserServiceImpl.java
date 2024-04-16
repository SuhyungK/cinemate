package movie.cinemate.cinemate.service.impl;

import lombok.RequiredArgsConstructor;
import movie.cinemate.cinemate.dto.user.JoinDto;
import movie.cinemate.cinemate.repository.UserDao;
import movie.cinemate.cinemate.repository.jdbctemplate.UserDaoImpl;
import movie.cinemate.cinemate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserDaoImpl userDao;

    public void join(JoinDto joinDto) {
        userDao.save(joinDto);
    }

    public boolean isIdDuplicate() {
        return false;
    }
}
