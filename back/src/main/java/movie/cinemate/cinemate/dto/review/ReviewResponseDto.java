package movie.cinemate.cinemate.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {
    private Long reviewId;
    private String id; // 유저의 ID
    private String content;
    private Float rate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
