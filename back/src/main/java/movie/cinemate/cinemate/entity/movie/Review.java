package movie.cinemate.cinemate.entity.movie;

import lombok.*;
import movie.cinemate.cinemate.dto.user.UserResponseDto;
import movie.cinemate.cinemate.entity.user.User;

import java.io.Writer;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private Long reviewId;
    private Long movieId;
    private String userId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Float rate;

    private User user;
}
