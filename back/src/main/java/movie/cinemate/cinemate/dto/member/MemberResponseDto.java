package movie.cinemate.cinemate.dto.member;

import lombok.*;

@Setter
@Getter
@Builder @NoArgsConstructor @AllArgsConstructor
public class MemberResponseDto {
    private String loginId;
    private String nickname;
}
