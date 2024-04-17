package movie.cinemate.cinemate.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private String userId;
    private String id;
    private String password;
    private String nickname;
    private LocalDateTime joinedAt;
    private LocalDateTime resignedAt;
}