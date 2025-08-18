package example.soloproject.global.presentation.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class SignInResultDto extends SignUpResultDto{
    private String access;
    private String refresh;
    private short attendance;

    @Builder
    public SignInResultDto(boolean success, int code, String msg, String token, String refresh, short attendance) {
        super(success, code, msg);
        this.access = token;
        this.refresh = refresh;
        this.attendance = attendance;
    }
}
