package movie.cinemate.cinemate.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinDto {
    @NotNull
    private String id;
    @NotNull
    private String password;
}
