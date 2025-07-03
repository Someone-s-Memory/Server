package example.soloproject.global.service;

import example.soloproject.global.presentation.dto.SignInCauseDto;
import example.soloproject.global.presentation.dto.SignInResultDto;
import example.soloproject.global.presentation.dto.SignUpCauseDto;
import example.soloproject.global.presentation.dto.SignUpResultDto;
import jakarta.servlet.http.HttpServletResponse;

public interface SignService {
    SignUpResultDto signUp(SignUpCauseDto request);

    SignInResultDto signIn(SignInCauseDto request, HttpServletResponse response) throws RuntimeException;
    SignInResultDto refreshToken(String refreshToken, HttpServletResponse response) throws RuntimeException;
}
