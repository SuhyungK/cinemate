package movie.cinemate.cinemate.dto.member;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
public class MemberDto {
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
}
