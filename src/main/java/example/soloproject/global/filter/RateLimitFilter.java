package example.soloproject.global.filter;

import example.soloproject.global.entity.UserDetails;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimitFilter extends OncePerRequestFilter {
    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger(RateLimitFilter.class);

    // API 요청 횟수 제한하는 버킷 -> 1분에 10회 요청 가능(6초에 1회 충전)
    private Bucket newBucket() {
        return Bucket.builder()
                .addLimit(Bandwidth.classic(10, Refill.greedy(10, Duration.ofMinutes(1))))
                .build();
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain) throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()){
            chain.doFilter(request, response);
        }
        Object principal = Objects.requireNonNull(auth).getPrincipal();
        String ID;
        String path = request.getRequestURI();

        if(principal instanceof UserDetails userDetails){
            ID = userDetails.getUId();
            logger.info("RateLimitFilter : doFilterInternal() - User ID : {}", ID);
        } else {
            logger.warn("RateLimitFilter : doFilterInternal() - Principal is not an instance of UserDetails: {}", principal);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
            return;
        }
        String key = ID + ":" + path;

        Bucket bucket = buckets.computeIfAbsent(key, k -> newBucket());
        if (bucket.tryConsume(1)) {
            logger.info("RateLimitFilter : doFilterInternal() - Request allowed for user ID : {}", ID);
            chain.doFilter(request, response);
        } else {
            logger.warn("RateLimitFilter : doFilterInternal() - Too many requests from user ID : {}", ID);
            response.setStatus(429);
            response.getWriter().write("Too many requests : Rate limit exceeded");
        }
    }
}
