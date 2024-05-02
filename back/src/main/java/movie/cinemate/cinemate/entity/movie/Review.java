package movie.cinemate.cinemate.entity.movie;

import lombok.*;
import movie.cinemate.cinemate.entity.member.Member;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private Long reviewId;
    private Long movieId;
    private String memberId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Float rate;

    private Member member;
}
