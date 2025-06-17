package example.soloproject.global.config;

import example.soloproject.global.jwt.CustomAccessDeniedHandler;
import example.soloproject.global.jwt.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션을 사용하지 않음

                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()) // 모든 요청을 허용

                .formLogin(form -> form.disable()) // 폼 로그인 비활성화

                .httpBasic(http -> http.disable())

                .exceptionHandling(exception -> exception // 나중에 custom 할 거임
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())// 인증되지 않은 경우
                        .accessDeniedHandler(new CustomAccessDeniedHandler())// 권한이 없는 경우
                )
        ; // HTTP Basic 인증 비활성화

        return httpSecurity.build();
    }
}
