package example.soloproject.global.entity.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignUpCauseDto {
    @NonNull
    private String userId;

    @NonNull
    private String password;

    @NonNull
    private String nickname;
}
