package movie.cinemate.cinemate.handler;

import movie.cinemate.cinemate.handler.exception.CustomUserException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ErrorExHandler {

    @ResponseBody
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> notFound(EmptyResultDataAccessException e) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.valueOf(400), "없는 사용자입니다"),
                HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(CustomUserException.class)
    public ResponseEntity<ErrorResponse> duplicatedError(Exception ex) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.valueOf(400), ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
