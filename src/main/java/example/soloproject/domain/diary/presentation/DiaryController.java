package example.soloproject.domain.diary.presentation;

import example.soloproject.domain.diary.presentation.dto.DiaryInsert;
import example.soloproject.domain.diary.service.DiaryService;
import example.soloproject.global.entity.UserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            return ResponseEntity.ok("일기 작성이 완료되었습니다.");
        }
        catch (Exception e) {
            logger.error("DiaryController : writeDiary() - 일기 작성 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.badRequest().body("일기 작성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
