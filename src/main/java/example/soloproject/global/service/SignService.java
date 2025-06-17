package example.soloproject.global.service;

import example.soloproject.global.entity.dto.SignInCauseDto;
import example.soloproject.global.entity.dto.SignInResultDto;
import example.soloproject.global.entity.dto.SignUpCauseDto;
import example.soloproject.global.entity.dto.SignUpResultDto;
import jakarta.servlet.http.HttpServletResponse;

public interface SignService {
    SignUpResultDto signUp(SignUpCauseDto request);

    SignInResultDto signIn(SignInCauseDto request, HttpServletResponse response) throws RuntimeException;
}
