package example.soloproject.domain.item.presentation;

import example.soloproject.domain.diary.presentation.dto.response.Message;
import example.soloproject.domain.item.service.ItemService;
import example.soloproject.global.entity.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/inventory" )
    public ResponseEntity<?> getInventory(@AuthenticationPrincipal UserDetails auth) {
        try {
            return ResponseEntity.ok(itemService.getInventory(auth));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(m("인벤토리 조회 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    public Message m (String message) {
        return Message.of(message);
    }
}
