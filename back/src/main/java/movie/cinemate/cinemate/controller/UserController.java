package movie.cinemate.cinemate.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import movie.cinemate.cinemate.dto.user.UserDto;
import movie.cinemate.cinemate.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/api/v1/user")
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
    @GetMapping("/join")
    public String showJoin(@ModelAttribute("joinForm") UserDto joinForm) {
        return "joinForm";
    }

    /**
     * 회원가입 API
     */
    @PostMapping("/join")
    public String join(@Valid @ModelAttribute UserDto joinDto) {
        ResponseCookie cookie = ResponseCookie.from("userId", userService.join(joinDto))
                                              .build();
        return "redirect:api/v1/user/login";
    }

    /**
     * 로그인 가입 화면
     */
    @GetMapping("/login")
    public String showLogin(@RequestParam(defaultValue = "/") String redirectURI,
                            @ModelAttribute("loginForm") UserDto userDto,
                            HttpServletResponse response
    ) {
        log.info("redirectURI : {}", redirectURI);
        return "loginForm";
    }

    /**
     * 로그인 API
     */
    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute("loginForm") UserDto userDto,
            BindingResult bindingResult,
            @RequestParam(defaultValue = "/api/v1/movie/movies") String redirectURI,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        if (bindingResult.hasErrors()) {
            log.info("bindingReulst.hasErrors userDto.getId={}", userDto.getId());
            return "loginForm";
        }

        String userId = userService.login(userDto);
        log.info("userService.login() userId={}", userId);
        if (userId == null) {
            bindingResult.reject("로그인 실패", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "loginForm";
        }

        // 쿠키 방식
//        Cookie cookie = new Cookie("userId", userId);
//        response.addCookie(cookie);

        // 세션 방식
        HttpSession session = request.getSession();
        session.setAttribute("loginUser", userId);

        log.info("리다이렉트 URI = {}", redirectURI);
        return "redirect:" + redirectURI;
    }
}
