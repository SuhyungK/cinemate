package movie.cinemate.cinemate.handler.exception;

import org.springframework.dao.IncorrectResultSizeDataAccessException;

public class CustomUserException extends IncorrectResultSizeDataAccessException {

    public CustomUserException(String msg, int expectedSize) {
        super(msg, expectedSize);
    }
}
