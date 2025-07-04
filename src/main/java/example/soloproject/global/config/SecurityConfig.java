package example.soloproject.global.config;

import example.soloproject.global.filter.RateLimitFilter;
import example.soloproject.global.filter.SpikeArrestFilter;
import example.soloproject.global.jwt.CustomAccessDeniedHandler;
import example.soloproject.global.jwt.CustomAuthenticationEntryPoint;
import example.soloproject.global.jwt.JwtAuthenticationFilter;
import example.soloproject.global.jwt.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션을 사용하지 않음

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/sign-in", "/sign-up", "/refresh").permitAll() // 로그인, 회원가입, 리프레시 토큰은 모두 허용
                        .anyRequest().authenticated())
                .logout(logout -> logout
                        .logoutUrl("/sign-out") // 로그아웃 URL 설정
                        .invalidateHttpSession(true) // 세션 무효화
                        .logoutSuccessHandler((request, response, authentication) -> {
                            logger.info("SecurityConfig : logoutSuccessHandler() - 로그아웃 요청이 들어왔습니다.");
                            // access 쿠키 삭제
                            logger.info("SecurityConfig : logoutSuccessHandler() - access 쿠키를 삭제합니다.");
                            Cookie accessCookie = new Cookie("access", null);
                            accessCookie.setMaxAge(0);
                            accessCookie.setPath("/");
                            accessCookie.setHttpOnly(true);
                            response.addCookie(accessCookie);

                            // refresh 쿠키 삭제
                            logger.info("SecurityConfig : logoutSuccessHandler() - refresh 쿠키를 삭제합니다.");
                            Cookie refreshCookie = new Cookie("refresh", null);
                            refreshCookie.setMaxAge(0);
                            refreshCookie.setPath("/");
                            refreshCookie.setHttpOnly(true);
                            response.addCookie(refreshCookie);

                            // 응답 코드 및 메시지 설정
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"message\": \"Logout success\"}");
                        })
                        .deleteCookies("JSESSIONID") // 쿠키 삭제
                )
                .formLogin(form -> form.disable()) // 폼 로그인 비활성화 -> 나중에 변경할 거

                .httpBasic(http -> http.disable()) // HTTP Basic 인증 비활성화 -> 나중에 변경할 거

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())// 인증되지 않은 경우
                        .accessDeniedHandler(new CustomAccessDeniedHandler())// 권한이 없는 경우
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new SpikeArrestFilter(), JwtAuthenticationFilter.class) // SpikeArrestFilter 추가
                .addFilterAfter(new RateLimitFilter(), SpikeArrestFilter.class) // RateLimitFilter 추가
        ;

        return httpSecurity.build();
    }
}
