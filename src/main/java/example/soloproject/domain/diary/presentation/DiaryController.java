package example.soloproject.domain.diary.presentation;

import example.soloproject.domain.diary.presentation.dto.request.DiaryInsert;
import example.soloproject.domain.diary.presentation.dto.request.DiaryUpdate;
import example.soloproject.domain.diary.presentation.dto.response.DiarySelected;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {
    private final Logger logger = LoggerFactory.getLogger(DiaryController.class);
    private final DiaryService diaryService;

    // update도 추가 예정 -> 새로운 API로 생성
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

    @GetMapping
    public ResponseEntity<?> getDiary(@RequestParam String date, @AuthenticationPrincipal UserDetails auth) {
        logger.info("DiaryController : getDiary() - 일기 조회 요청이 들어왔습니다.");
        try {
            List<DiarySelected> diarySelectedList = diaryService.getDiary(date, auth);
            logger.info("DiaryController : getDiary() - 일기 조회가 완료되었습니다.");
            return ResponseEntity.ok(diarySelectedList);
        }
        catch (Exception e) {
            logger.error("DiaryController : getDiary() - 일기 조회 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.badRequest().body(m("일기 조회 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllDiary(@AuthenticationPrincipal UserDetails auth) {
        logger.info("DiaryController : getAllDiary() - 모든 일기 조회 요청이 들어왔습니다.");
        try {
            List<DiarySelected> diarySelectedList = diaryService.getAllDiary(auth);
            logger.info("DiaryController : getAllDiary() - 모든 일기 조회가 완료되었습니다.");
            return ResponseEntity.ok(diarySelectedList);
        } catch (Exception e) {
            logger.error("DiaryController : getAllDiary() - 모든 일기 조회 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.badRequest().body(m("모든 일기 조회 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateDiary(@Valid @RequestBody DiaryUpdate diaryUpdate, @AuthenticationPrincipal UserDetails auth) {
        logger.info("DiaryController : updateDiary() - 일기 수정 요청이 들어왔습니다.");
        try {
            diaryService.updateDiary(diaryUpdate, auth);
            logger.info("DiaryController : updateDiary() - 일기 수정이 완료되었습니다.");
            return ResponseEntity.ok(m("일기 수정이 완료되었습니다."));
        } catch (Exception e) {
            logger.error("DiaryController : updateDiary() - 일기 수정 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.badRequest().body(m("일기 수정 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteDiary(@RequestParam String date, @RequestParam String title, @AuthenticationPrincipal UserDetails auth) {
        logger.info("DiaryController : deleteDiary() - 일기 삭제 요청이 들어왔습니다.");
        try {
            diaryService.deleteDiary(date, title, auth);
            logger.info("DiaryController : deleteDiary() - 일기 삭제가 완료되었습니다.");
            return ResponseEntity.ok(m("일기 삭제가 완료되었습니다."));
        } catch (Exception e) {
            logger.error("DiaryController : deleteDiary() - 일기 삭제 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.badRequest().body(m("일기 삭제 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getDiaryDetail(@RequestParam Long diaryId, @AuthenticationPrincipal UserDetails auth) {
        logger.info("DiaryController : getDiaryDetail() - 일기 상세 조회 요청이 들어왔습니다.");
        try {
            DiarySelected diarySelected = diaryService.getDiaryDetail(diaryId, auth);
            logger.info("DiaryController : getDiaryDetail() - 일기 상세 조회가 완료되었습니다.");
            return ResponseEntity.ok(diarySelected);
        } catch (Exception e) {
            logger.error("DiaryController : getDiaryDetail() - 일기 상세 조회 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.badRequest().body(m("일기 상세 조회 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }
}
