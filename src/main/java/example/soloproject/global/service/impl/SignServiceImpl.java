package example.soloproject.global.service.impl;

import example.soloproject.global.entity.User;
import example.soloproject.global.presentation.dto.SignInCauseDto;
import example.soloproject.global.presentation.dto.SignInResultDto;
import example.soloproject.global.presentation.dto.SignUpCauseDto;
import example.soloproject.global.presentation.dto.SignUpResultDto;
import example.soloproject.global.jwt.CommonResponse;
import example.soloproject.global.jwt.JwtTokenProvider;
import example.soloproject.global.repository.UserRepository;
import example.soloproject.global.service.CookieUtil;
import example.soloproject.global.service.SignService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {
    private final Logger logger = LoggerFactory.getLogger(SignServiceImpl.class);

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final CookieUtil cookieUtil;

    public SignUpResultDto signUp(SignUpCauseDto request) {
        logger.info("SignServiceImpl : signUp() 실행 - 회원 가입 정보 전달");
        User user = User.builder()
                .nickname(request.getNickname())
                .UId(request.getUserId())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singletonList("ROLE_USER"))
                .email(request.getEmail())
                .build();
        User savedUser = userRepository.save(user);
        SignUpResultDto signUpResultDto = new SignUpResultDto();
        logger.info("SignServiceImpl : signUp() 실행 - userEntity 값이 들어왔는지 확인 후 결과값 주입");
        if (!savedUser.getNickname().isEmpty()) {
            logger.info("SignServiceImpl : signUp() 실행 - userEntity 정상 처리 완료");
            setSuccessResult(signUpResultDto);
        } else {
            logger.info("SignServiceImpl : signUp() 실행 - userEntity 실패 처리 완료");
            setFailResult(signUpResultDto);
        }
        return signUpResultDto;
    }

    public SignInResultDto signIn(SignInCauseDto request, HttpServletResponse response) throws RuntimeException {
        logger.info("SignServiceImpl : signIn() 실행 - 로그인 정보 전달");
        User user = userRepository.findByUId(request.getUserId());
        logger.info("SignServiceImpl : signIn() 실행 - 패스워드 비교 수행");

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            logger.info("SignServiceImpl : signIn() 실행 - 패스워드 불일치");
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        logger.info("SignServiceImpl : signIn() 실행 - 패스워드 일치");

        logger.info("SignServiceImpl : signIn() 실행 - SignInResultDto 객체 생성");
        SignInResultDto signInResultDto = SignInResultDto.builder()
                .token(jwtTokenProvider.createAccess(String.valueOf(user.getUId())
                        ,user.getRoles()))
                .refresh(jwtTokenProvider.createRefresh(String.valueOf(user.getUId())
                        ,user.getRoles()))
                .build();

        logger.info("SignServiceImpl : signIn() 실행 - Cookie에 token값 주입");
        cookieUtil.addJwtCookie(response,signInResultDto.getAccess(), signInResultDto.getRefresh());

        logger.info("SignServiceImpl : signIn() 실행 - SignInResultDto 객체에 값 주입");
        setSuccessResult(signInResultDto);

        return signInResultDto;
    }

    public SignInResultDto refreshToken(String refreshToken, HttpServletResponse response) throws RuntimeException {
        logger.info("SignServiceImpl : refreshToken() 실행 - 리프레시 토큰 갱신 요청");
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            logger.error("SignServiceImpl : refreshToken() 실행 - 유효하지 않은 리프레시 토큰");
            throw new RuntimeException("유효하지 않은 리프레시 토큰입니다.");
        }

        String userId = jwtTokenProvider.getUserID(refreshToken);
        User user = userRepository.findByUId(userId);

        SignInResultDto signInResultDto = SignInResultDto.builder()
                .token(jwtTokenProvider.createAccess(user.getUId(), user.getRoles()))
                .refresh(jwtTokenProvider.createRefresh(user.getUId(), user.getRoles()))
                .build();

        cookieUtil.addJwtCookie(response, signInResultDto.getAccess(), signInResultDto.getRefresh());
        setSuccessResult(signInResultDto);
        return signInResultDto;
    }

    private void setSuccessResult(SignUpResultDto result){
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    private void setFailResult(SignUpResultDto result){
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }
}
