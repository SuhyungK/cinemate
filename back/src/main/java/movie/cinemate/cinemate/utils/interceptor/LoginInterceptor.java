package movie.cinemate.cinemate.utils.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import movie.cinemate.cinemate.utils.session.SessionData;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        log.info("preHandle : [{}][{}][{}]", request.getRequestURL(), handler, getClass());

        if (session == null || session.getAttribute(SessionData.SESSION_CONST) == null) {
            log.info("인증 안 된 사용자 로그인 리다이렉트 요청 requestURL = {}", request.getRequestURL());
            response.sendRedirect("/api/v1/user/login?redirectURI=" + request.getRequestURI());
            return false;
        }
        log.info("preHandle true 반환");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle : [{}][{}][{}]", request.getRequestURL(), handler, getClass());

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion : [{}][{}][{}]", request.getRequestURL(), handler, getClass());
    }
}