package movie.cinemate.cinemate.dto.review;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewEditRequestDto {
    private Long reviewId;
    private String content; // 내용
    private Float rate; // 평점
    private LocalDateTime createdAt; // 작성일시
}
