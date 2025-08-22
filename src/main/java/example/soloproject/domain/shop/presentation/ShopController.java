package example.soloproject.domain.shop.presentation;

import example.soloproject.domain.shop.service.ShopService;
import example.soloproject.global.entity.UserDetails;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("shop")
public class ShopController {
    private final Logger logger = LoggerFactory.getLogger(ShopController.class);
    private final ShopService shopService;

    @GetMapping("/get")
    public ResponseEntity<?> getShop(@AuthenticationPrincipal UserDetails auth) {
        logger.info("ShopController : getShop() - 상점 정보 조회 요청이 들어왔습니다.");
        return ResponseEntity.ok(shopService.getShop());
    }
}
