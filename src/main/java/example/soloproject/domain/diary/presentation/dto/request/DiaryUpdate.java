package example.soloproject.domain.diary.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter

public class DiaryUpdate {
    //원래 제목
    @NotNull
    private String title;
    //원래 날짜
    @NotNull
    private String date;

    //변경될 내용
    @NotNull
    private String content;
    //변경될 기분
    @NotNull
    private String feeling;
    //변경될 날씨
    @NotNull
    private String weather;
    //변경될 사진
    private List<String> pictures;
    //변경할 제목
    @NotNull
    private String change;
}
