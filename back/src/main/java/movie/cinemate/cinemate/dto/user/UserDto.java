package movie.cinemate.cinemate.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
public class UserDto {
    @NotBlank
    private String id;
    @NotBlank
    private String password;
}
