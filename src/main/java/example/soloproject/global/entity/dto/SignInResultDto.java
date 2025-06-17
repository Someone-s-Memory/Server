package example.soloproject.global.entity.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class SignInResultDto extends SignUpResultDto{
    private String token;
    private String refresh;

    @Builder
    public SignInResultDto(boolean success, int code, String msg, String token, String refresh) {
        super(success, code, msg);
        this.token = token;
        this.refresh = refresh;
    }
}
