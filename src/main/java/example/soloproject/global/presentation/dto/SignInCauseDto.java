package example.soloproject.global.presentation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignInCauseDto {
    @NotNull
    private String userId;

    @NotNull
    private String password;
}
