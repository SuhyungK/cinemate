package movie.cinemate.cinemate.handler.exception;

import org.springframework.dao.IncorrectResultSizeDataAccessException;

public class CustomUserException extends IncorrectResultSizeDataAccessException {

    public CustomUserException(String msg, int expectedSize) {
        super(msg, expectedSize);
    }

    public CustomUserException(String msg, int expectedSize, Throwable ex) {
        super(msg, expectedSize, ex);
    }

    public CustomUserException(String msg, int expectedSize, int actualSize) {
        super(msg, expectedSize, actualSize);
    }

    public CustomUserException(String msg, int expectedSize, int actualSize, Throwable ex) {
        super(msg, expectedSize, actualSize, ex);
    }

    public CustomUserException(int expectedSize) {
        super(expectedSize);
    }

    public CustomUserException(int expectedSize, int actualSize) {
        super(expectedSize, actualSize);
    }
}
