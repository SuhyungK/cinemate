package movie.cinemate.cinemate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import movie.cinemate.cinemate.dto.review.ReviewEditRequestDto;
import movie.cinemate.cinemate.dto.review.ReviewRequestDto;
import movie.cinemate.cinemate.entity.member.Member;
import movie.cinemate.cinemate.entity.movie.Review;
import movie.cinemate.cinemate.repository.jdbctemplate.ReviewDaoImpl;
import movie.cinemate.cinemate.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewDaoImpl reviewDao;

    @Autowired
    ModelMapper modelMapper;

    /**
     * TODO 1. 리뷰 작성 - 로그인 된 작성자인 경우에 대해서만 가능
     */
    public Long write(ReviewRequestDto dto, Member member) {
        Review review = modelMapper.map(dto, Review.class);
        review.setMemberId(member.getMemberId());
        return reviewDao.add(review);
    }

    /**
     * TODO 2. 리뷰 수정 - 작성자와 일치하는 경우에만 수정 가능
     */
    public void edit(ReviewEditRequestDto dto, String memberId) {
//        if (!reviewDao.findReviewByReviewId(dto.getReviewId())
//                      .equals(userId)) {
//            return;
//        }
//        reviewDao.updateReview(dto);
    }

    /**
     * TODO 3. 리뷰 삭제 - 작성자와 일치하는 경우에만 삭제 가능
     */
    public void delete(Long reviewId, String memberId) {

        if (!reviewDao.findByReviewId(reviewId)
                      .equals(memberId)) {
            // 작성자와 일치하지 않는 경우 작성 불가
            return;
        }
        reviewDao.delete(reviewId);
    }
}
