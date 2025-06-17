package example.soloproject.global.service;

import example.soloproject.global.entity.dto.SignInResultDto;
import example.soloproject.global.entity.dto.SignUpResultDto;
import jakarta.servlet.http.HttpServletResponse;

public interface SignService {
    SignUpResultDto signUp(String id, String password, String name, String role);

    SignInResultDto signIn(String id, String password, HttpServletResponse response) throws RuntimeException;
}
