package movie.cinemate.cinemate.entity.user;

import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@NoArgsConstructor
public class User {
    private Long userId;
    private String id;
    private String password;
    private String nickname;
    private LocalDateTime joinedAt;
    private LocalDateTime resignedAt;
}