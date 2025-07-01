package example.soloproject.domain.diary.presentation;

import example.soloproject.domain.diary.presentation.dto.request.DiaryInsert;
import example.soloproject.domain.diary.presentation.dto.response.Message;
import example.soloproject.domain.diary.service.DiaryService;
import example.soloproject.global.entity.UserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {
    private final Logger logger = LoggerFactory.getLogger(DiaryController.class);
    private final DiaryService diaryService;

    // update도 추가 예정
    @PostMapping("/write")
    public ResponseEntity<?> writeDiary(@Valid @RequestBody DiaryInsert diaryInsert, @AuthenticationPrincipal UserDetails auth) {
        logger.info(("DiaryController : writeDiary() - 일기 작성 요청이 들어왔습니다."));
        try {
            diaryService.writeDiary(diaryInsert, auth);
            logger.info("DiaryController : writeDiary() - 일기 작성이 완료되었습니다.");
            return ResponseEntity.ok(m("일기 작성이 완료되었습니다."));
        }
        catch (Exception e) {
            logger.error("DiaryController : writeDiary() - 일기 작성 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.badRequest().body(m("일기 작성 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    public Message m (String message) {
        return Message.of(message);
    }

//    @GetMapping
//    public ResponseEntity<?> getDiary() {
//        logger.info("DiaryController : getDiary() - 일기 조회 요청이 들어왔습니다.");
//        try {
//
//        }
//        catch (Exception e) {
//            logger.error("DiaryController : getDiary() - 일기 조회 중 오류 발생: {}", e.getMessage());
//            return ResponseEntity.badRequest().body(m("일기 조회 중 오류가 발생했습니다: " + e.getMessage()));
//        }
//    }
}
