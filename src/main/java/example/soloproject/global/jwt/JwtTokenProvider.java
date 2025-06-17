package example.soloproject.global.jwt;

import example.soloproject.global.service.UserDetailsService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final UserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String secretKey;
    private final long tokenValidMillisecond = 1000L * 60 * 60; // 1시간
    private final long refreshTokenValidMillisecond = 1000L * 60 * 60 * 24 * 7; // 7일

    @PostConstruct
    protected void init() {
        logger.info("JwtTokenProvider : init() 실행 - secretKey 초기화 시작");
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        logger.info("JwtTokenProvider : init() 실행 - secretKey 초기화 완료");
    }


}
