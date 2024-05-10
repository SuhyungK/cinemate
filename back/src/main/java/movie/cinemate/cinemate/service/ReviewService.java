package movie.cinemate.cinemate.service;

import movie.cinemate.cinemate.dto.review.ReviewEditRequestDto;
import movie.cinemate.cinemate.dto.review.ReviewRequestDto;
import movie.cinemate.cinemate.entity.member.Member;

public interface ReviewService {

    Long write(ReviewRequestDto dto, Member member);
    void edit(ReviewEditRequestDto dto, String memberId);
    void delete(Long reviewId, String memberId);
}
