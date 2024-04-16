package movie.cinemate.cinemate.handler;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ErrorExHandler {

    @ResponseBody
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> notFound() {
        return new ResponseEntity<>(new ErrorResponse("404", null), HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(IncorrectResultSizeDataAccessException.class)
    public ResponseEntity<ErrorResponse> duplicatedError() {
        return new ResponseEntity<>(new ErrorResponse("400", HttpStatusCode.valueOf(404)), HttpStatus.BAD_REQUEST);
    }
}
