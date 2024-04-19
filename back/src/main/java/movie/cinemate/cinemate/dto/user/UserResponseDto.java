package movie.cinemate.cinemate.dto.user;

import lombok.*;

@Setter
@Getter
@Builder @NoArgsConstructor @AllArgsConstructor
public class UserResponseDto {
    private String id;
    private String nickname;
}
