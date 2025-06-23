package example.soloproject.domain.diary.presentation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DiaryInsert {
    @NotNull
    private String title;
    @NotNull
    private String content;
}
