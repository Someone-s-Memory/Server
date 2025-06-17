package example.soloproject.global.entity.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignInCauseDto {
    @NonNull
    private String id;

    @NonNull
    private String password;
}
