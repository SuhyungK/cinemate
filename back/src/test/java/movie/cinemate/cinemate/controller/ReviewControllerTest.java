package movie.cinemate.cinemate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import movie.cinemate.cinemate.dto.review.ReviewRequestDto;
import movie.cinemate.cinemate.entity.member.Member;
import movie.cinemate.cinemate.repository.ReviewDao;
import movie.cinemate.cinemate.service.ReviewService;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static movie.cinemate.cinemate.util.Data.*;
import static movie.cinemate.cinemate.utils.session.SessionData.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@Slf4j
@WebMvcTest(controllers = ReviewController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ReviewControllerTest {

    @Autowired
    MockMvc mockMvc;

//    MockHttpSession session;

//    @Mock
//    HttpSession session;

    @MockBean
    ReviewService service;

    @MockBean
    ReviewDao dao;

    Long movieId = 1L;
    Member member;
    MockHttpSession session;

    @BeforeEach
    void before() {
        member = Member.builder()
                       .memberId(MEMBER_ID)
                       .build();
        session = new MockHttpSession();
        session.setAttribute(SESSION_CONST, member);
    }

    @Test
    void ReviewController_write_returnCreated() throws Exception {
        log.info("movieId = {}", movieId);
        ReviewRequestDto reviewDto = ReviewRequestDto.builder()
                                                     .movieId(movieId)
                                                     .content(RandomString.make(50))
                                                     .rate(3.5F)
                                                     .build();

        when(service.write(Mockito.any(ReviewRequestDto.class), Mockito.any(Member.class))).thenReturn(1L);

        assertThat(service.write(reviewDto, member)).isEqualTo(1L);

        mockMvc.perform(post("/api/v1/movies/{movieId}/reviews", movieId)
                       .contentType(MediaType.MULTIPART_FORM_DATA)
                       .characterEncoding("utf-8")
                       .session(session)
                       .param("movieId", String.valueOf(movieId))
                       .param("content", reviewDto.getContent())
                       .param("rate", String.valueOf(reviewDto.getRate()))
               )
               .andDo(print())
               .andExpect(status().isCreated())
               .andExpect(header().exists(HttpHeaders.LOCATION))
               .andExpect(jsonPath("_links.self.href").exists())
        ;
    }
}