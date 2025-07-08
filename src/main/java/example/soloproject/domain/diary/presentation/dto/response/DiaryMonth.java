package example.soloproject.domain.diary.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class DiaryMonth {
    private Map<String, Short> feelings;
    private Short every;
    private Short sequence;
}
