package example.soloproject.global.entity.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignUpCauseDto {
    @NonNull
    private String id;

    @NonNull
    private String password;

    @NonNull
    private String name;

    @NonNull
    private String role;
}
