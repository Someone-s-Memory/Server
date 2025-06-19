package example.soloproject.global.config;

import example.soloproject.global.jwt.CustomAccessDeniedHandler;
import example.soloproject.global.jwt.CustomAuthenticationEntryPoint;
import example.soloproject.global.jwt.JwtAuthenticationFilter;
import example.soloproject.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
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
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션을 사용하지 않음

                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()) // 모든 요청을 허용 -> 나중에 변경할 거
                .logout(logout -> logout
                        .logoutUrl("/sign-out") // 로그아웃 URL 설정
                        .logoutSuccessUrl("/logout") // 로그아웃 성공 시 리다이렉트 URL 설정
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // 쿠키 삭제
                )
                .formLogin(form -> form.disable()) // 폼 로그인 비활성화 -> 나중에 변경할 거

                .httpBasic(http -> http.disable()) // HTTP Basic 인증 비활성화 -> 나중에 변경할 거

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())// 인증되지 않은 경우
                        .accessDeniedHandler(new CustomAccessDeniedHandler())// 권한이 없는 경우
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
        ;

        return httpSecurity.build();
    }
}
