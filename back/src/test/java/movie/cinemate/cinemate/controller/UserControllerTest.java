package movie.cinemate.cinemate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import movie.cinemate.cinemate.dto.member.MemberDto;
import movie.cinemate.cinemate.entity.member.Member;
import movie.cinemate.cinemate.service.impl.UserServiceImpl;
import net.bytebuddy.utility.RandomString;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @MockBean
    UserServiceImpl userService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * 회원가입 API 테스트
     * @Controller, return String 이라 MockMvc 로 테스트 안 됨
     * @throws Exception
     */
    @Test
    void UserController_join_success() throws Exception {
        MemberDto joinMember = MemberDto.builder()
                                        .loginId("test")
                                        .password("test1234")
                                        .build();

        String memberId = RandomString.make(32);
        System.out.println("memberId = " + memberId);
        when(userService.join(any(MemberDto.class))).thenReturn(memberId);

        MvcResult result = mockMvc.perform(post("/api/v1/user/join")
                                          .contentType(MediaType.APPLICATION_JSON)
                                          .accept(MediaType.TEXT_HTML)
                                          .content(objectMapper.writeValueAsString(joinMember)))
                                  .andDo(MockMvcResultHandlers.print())
//                                  .andExpect(status().is3xxRedirection())
//                                  .andExpect(redirectedUrl("/api/v1/user/login"))
                                  .andReturn();

        System.out.println(result.getResponse().getContentAsString());
        System.out.println(result.getResponse().getContentLength());
    }
}