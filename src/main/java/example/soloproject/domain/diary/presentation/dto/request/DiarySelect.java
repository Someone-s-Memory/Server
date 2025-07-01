package example.soloproject.domain.diary.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DiarySelect {
    @NotNull
    private String date;
}
