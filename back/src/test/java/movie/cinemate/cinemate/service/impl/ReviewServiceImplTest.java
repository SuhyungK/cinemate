package movie.cinemate.cinemate.service.impl;

import movie.cinemate.cinemate.repository.jdbctemplate.ReviewDaoImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ReviewServiceImplTest {

    @Mock
    private ReviewDaoImpl reviewDao;

    @InjectMocks
    // @Mock 어노테이션이 붙은 객체를 @InjectMocks에 붙은 객체에 주입시키기
    private ReviewServiceImpl reviewService;

    @Test
    void ReviewServiceImpl_WriteReview_ReturnReviewId() {
        // Assertions

    }

}