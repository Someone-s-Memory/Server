package example.soloproject.domain.diary.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class DiarySelected {
    private Long id;
    private String title;
    private String content;
    private String feeling;
    private String weather;
    private String date;
    private List<String> pictures;
    private String userID;
}
