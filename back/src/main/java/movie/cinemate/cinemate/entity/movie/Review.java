package movie.cinemate.cinemate.entity.movie;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class Review {
    private Long reviewId;
    private Long movieId;
    private String userId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Float rate;
}
