package movie.cinemate.cinemate.entity.movie;

import java.time.LocalDateTime;

public class Review {
    private Long reviewId;
    private Long movieId;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Float rate;
}
