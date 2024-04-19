package movie.cinemate.cinemate.entity.user;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userId;
    private String id;
    private String password;
    private String nickname;
    private LocalDateTime joinedAt;
    private LocalDateTime resignedAt;
}