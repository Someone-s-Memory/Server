package example.soloproject.domain.shop.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ShopController {

    @GetMapping("/shop")
    public ResponseEntity<?> getShop() {
        return ResponseEntity.ok("상점 정보가 성공적으로 조회되었습니다.");
    }
}
