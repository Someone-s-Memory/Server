package example.soloproject.domain.pet.presentation;

import example.soloproject.domain.diary.presentation.dto.response.Message;
import example.soloproject.domain.pet.service.PetService;
import example.soloproject.global.entity.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pet")
public class PetController {
    private final PetService petService;

    @GetMapping("get")
    public ResponseEntity<?> getPet(@AuthenticationPrincipal UserDetails auth) {
        try {
            return ResponseEntity.ok(petService.getPets(auth));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(m("팻의 정보를 조회하는 중 에러가 발생했습니다: "+ e.getMessage()));
        }
    }

    public Message m (String message) {
        return Message.of(message);
    }
}
