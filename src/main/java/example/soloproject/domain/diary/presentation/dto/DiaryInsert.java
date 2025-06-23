package example.soloproject.domain.diary.presentation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DiaryInsert {
    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private String filling;
    @NotNull
    private String weather;
    private String picture;
    @NotNull
    private String date;
}
