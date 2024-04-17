package movie.cinemate.cinemate.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
public class UserDto {
    @NotBlank
    private String id;
    @NotBlank
    private String password;
}
