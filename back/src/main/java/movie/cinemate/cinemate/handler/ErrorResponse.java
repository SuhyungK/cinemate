package movie.cinemate.cinemate.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    HttpStatusCode code;
    String message;
}
