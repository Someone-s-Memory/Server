package example.soloproject.global.presentation.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignUpCauseDto {
    @NotNull
    private String userId;

    @NotNull
    private String password;

    @NotNull
    private String passwordCheck;

    @NotNull
    private String nickname;

    @Email
    private String email;

    @AssertTrue(message = "비밀번호가 일치하지 않습니다.")
    public boolean isPasswordMatching() {
        return password.equals(passwordCheck);
    }
}
