package example.soloproject.global.jwt;

import example.soloproject.global.entity.UserDetails;
import example.soloproject.global.service.UserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

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

    public String createRefresh(String userID, List<String> roles) {
        logger.info("JwtTokenProvider : createRefresh() 실행 - 리프레쉬 토큰 생성 시작");

        Claims claims = Jwts.claims().setSubject(userID);
        claims.put("roles", roles);
        Date now = new Date();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidMillisecond))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        logger.info("JwtTokenProvider : createRefresh() 실행 - 리프레쉬 토큰 생성 완료");

        return token;
    }

    public String createAccess(String userID, List<String> roles) {
        logger.info("JwtTokenProvider : createAccess() 실행 - 액세스 토큰 생성 시작");

        Date now = new Date();
        Claims claims = Jwts.claims().setSubject(userID);
        claims.put("roles", roles);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        logger.info("JwtTokenProvider : createAccess() 실행 - 액세스 토큰 생성 완료");
        return token;
    }
    public String getUserID(String token) {
        logger.info("JwtTokenProvider : getUserID() 실행 - 토큰으로부터 유저 정보 가져오기 시작");
        String info = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        logger.info("JwtTokenProvider : getUserID() 실행 - 토큰으로부터 유저 정보 가져오기 완료");
        return info;
    }

    public Authentication getAuthentication(String token) {
        logger.info("JwtTokenProvider : getAuthentication() 실행 - 토큰으로부터 인증 정보 가져오기 시작");
        UserDetails userDetails = userDetailsService.loadUserByUserID(this.getUserID(token));
        logger.info(userDetails.getUsername() + " 유저 정보 확인");
        logger.info("JwtTokenProvider : getAuthentication() 실행 - 토큰으로부터 인증 정보 가져오기 완료");
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public List<String> getRoles(String token) {
        logger.info("JwtTokenProvider : getRoles() 실행 - 토큰으로부터 권한 정보 가져오기 시작");
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        List<String> roles = claims.get("roles", List.class);
        logger.info("JwtTokenProvider : getRoles() 실행 - 토큰으로부터 권한 정보 가져오기 완료");
        return roles;
    }

    public String resolveToken(HttpServletRequest request) {
        logger.info("JwtTokenProvider : resolveToken() 실행 - 헤더 또는 쿠키에서 토큰 값 추출");

        // 1. Authorization 헤더 우선
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            logger.info("Authorization 헤더에서 토큰 추출 성공");
            return bearerToken.substring(7);
        }

        // 2. 쿠키에 access 토큰 있는지 확인
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("access".equals(cookie.getName())) {
                    logger.info("쿠키에서 access 토큰 추출 성공");
                    return cookie.getValue();
                }
            }
        }

        logger.warn("토큰 추출 실패 - Authorization 헤더 및 access 쿠키 모두 없음");
        return null;
    }
    public boolean validateToken(String token){
        logger.info("JwtTokenProvider : validateToken() 실행 - 토큰 유효 체크 시작");
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            logger.error("JwtTokenProvider : validateToken() 실행 - 토큰 유효 체크 실패: {}", e.getMessage());
            return false;
        }
    }
}
