package example.soloproject.global.jwt;

import example.soloproject.global.entity.UserDetails;
import example.soloproject.global.service.UserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final UserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String secretKey;
    Key key;
    private final long tokenValidMillisecond = 1000L * 60 * 60; // 1시간
    private final long refreshTokenValidMillisecond = 1000L * 60 * 60 * 24 * 7; // 7일

    @PostConstruct
    protected void init() {
        logger.info("JwtTokenProvider : init() 실행 - secretKey 초기화 시작");
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        logger.info("JwtTokenProvider : init() 실행 - secretKey 초기화 완료");
    }

    public String createRefresh(String niekName){
        logger.info("JwtTokenProvider : createRefresh() 실행 - 리프레쉬 토큰 생성 시작");

//        Claims claims = Jwts.claims().setSubject(niekName); // Claims을 쓸 이유가 없음 Why -> 유저의 룰이 없기 때문
        Date now = new Date();

        String token = Jwts.builder()
//                .setClaims(claims)
                .setSubject(niekName)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidMillisecond))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        logger.info("JwtTokenProvider : createRefresh() 실행 - 리프레쉬 토큰 생성 완료");

        return token;
    }

    public String createAccess(String niekName) {
        logger.info("JwtTokenProvider : createAccess() 실행 - 액세스 토큰 생성 시작");

        Date now = new Date();

        String token = Jwts.builder()
                .setSubject(niekName)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        logger.info("JwtTokenProvider : createAccess() 실행 - 액세스 토큰 생성 완료");
        return token;
    }
}
