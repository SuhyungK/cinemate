package movie.cinemate.cinemate.entity.member;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private String memberId;
    private String loginId;
    private String password;
    private String nickname;
    private LocalDateTime joinedAt;
    private LocalDateTime resignedAt;
}