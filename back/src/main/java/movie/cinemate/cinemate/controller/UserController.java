package movie.cinemate.cinemate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import movie.cinemate.cinemate.dto.user.JoinDto;
import movie.cinemate.cinemate.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/v1/user")
@Controller
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserServiceImpl userService;

    /**
     * 유저 정보
     * 아이디, 닉네임
     * 관심 영화
     * 작성한 영화 리뷰 및 댓글
     * 작성한 커뮤니티 글
     * 예매한 영화
     *
     * @param userId
     */
    @GetMapping
    public void gerUser(@RequestParam Long userId) {

    }

    /**
     * 회원 가입 화면
     */
    @GetMapping("/login")
    public String showLogin() {
        return "loginForm";
    }

    /**
     * 회원가입 API
     */
    @PostMapping("/join")
    public String join(@ModelAttribute JoinDto joinDto) {
        userService.join(joinDto);
        return "redirect:/api/v1/user/login";
    }

    /**
     * 로그인 화면
     */
    @GetMapping("/join")
    public String showJoin() {
        return "joinForm";
    }


    /**
     * 로그인 API
     */
    @PostMapping("/login")
    public void login() {

    }
}
