package example.soloproject.domain.shop.presentation;

import example.soloproject.domain.diary.presentation.dto.response.Message;
import example.soloproject.domain.shop.presentation.dto.BuyDto;
import example.soloproject.domain.shop.presentation.dto.ExhibitionDto;
import example.soloproject.domain.shop.service.ShopService;
import example.soloproject.global.entity.UserDetails;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopController {
    private final Logger logger = LoggerFactory.getLogger(ShopController.class);
    private final ShopService shopService;

    @GetMapping("/get")
    public ResponseEntity<?> getShop(@AuthenticationPrincipal UserDetails auth) {
        logger.info("ShopController : getShop() - 상점 정보 조회 요청이 들어왔습니다.");
        try {
            return ResponseEntity.ok(shopService.getShop());
        }
        catch (Exception e) {
            logger.error("ShopController : getShop() - 상점 정보 조회 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.badRequest().body(m("상점 정보 조회 중 오류가 발생했습니다: " + e.getMessage()));
        }

    }

    @PostMapping("/exhibit")
    public ResponseEntity<?> exhibition(@AuthenticationPrincipal UserDetails auth, @RequestBody List<ExhibitionDto> request) {
        logger.info("ShopController : exhibition() - 상점 진열 요청이 들어왔습니다.");
        try {
            shopService.exhibition(auth, request);
            return ResponseEntity.ok("상점 진열이 완료되었습니다.");
        }
        catch (Exception e) {
            logger.error("ShopController : exhibition() - 상점 진열 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.badRequest().body(m("상점 진열 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    @PostMapping("/buy")
    public ResponseEntity<?> buyItem(@AuthenticationPrincipal UserDetails auth, @RequestBody BuyDto request) {
        logger.info("ShopController : buyItem() - 아이템 구매 요청이 들어왔습니다.");
        try {
            logger.info("ShopController : buyItem() - 아이템 ID: {}", request);
            shopService.buyItem(request, auth);
            return ResponseEntity.ok("아이템 구매가 완료되었습니다.");
        } catch (Exception e) {
            logger.error("ShopController : buyItem() - 아이템 구매 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.badRequest().body(m("아이템 구매 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    @PutMapping("/sold")
    public ResponseEntity<?> sold(@AuthenticationPrincipal UserDetails auth, @RequestBody BuyDto request) {
        logger.info("ShopController : sold() - 상점 판매 완료 요청이 들어왔습니다.");
        try {
            shopService.sold(auth, request);
            return ResponseEntity.ok("상점 판매가 완료되었습니다.");
        } catch (Exception e) {
            logger.error("ShopController : sold() - 상점 판매 완료 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.badRequest().body(m("상점 판매 완료 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    public Message m (String message) {
        return Message.of(message);
    }

}
