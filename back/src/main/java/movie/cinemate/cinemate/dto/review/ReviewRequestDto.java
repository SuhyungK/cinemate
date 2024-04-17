package movie.cinemate.cinemate.dto.review;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewRequestDto {
    private Long movieId; // 영화ID
    private String content; // 내용
    private Float rate; // 평점
}
