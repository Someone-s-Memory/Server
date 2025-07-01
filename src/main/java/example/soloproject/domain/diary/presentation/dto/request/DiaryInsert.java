package example.soloproject.domain.diary.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class DiaryInsert {
    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private String feeling;
    @NotNull
    private String weather;
    private List<String> pictures;
    @NotNull
    private String date;
}
