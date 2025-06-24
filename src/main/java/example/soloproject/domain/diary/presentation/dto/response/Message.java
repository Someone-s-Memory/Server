package example.soloproject.domain.diary.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Message {
    private String message;

    public static Message of(String message) {
        return Message.builder()
                .message(message)
                .build();
    }
}
