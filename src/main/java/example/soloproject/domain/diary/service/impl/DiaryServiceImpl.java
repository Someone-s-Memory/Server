package example.soloproject.domain.diary.service.impl;

import example.soloproject.domain.diary.entity.Diary;
import example.soloproject.domain.diary.presentation.dto.request.DiaryDelete;
import example.soloproject.domain.diary.presentation.dto.request.DiaryInsert;
import example.soloproject.domain.diary.presentation.dto.request.DiaryUpdate;
import example.soloproject.domain.diary.presentation.dto.response.DiarySelected;
import example.soloproject.domain.diary.repository.DiaryRepository;
import example.soloproject.domain.diary.service.DiaryService;
import example.soloproject.global.entity.User;
import example.soloproject.global.entity.UserDetails;
import example.soloproject.global.service.impl.MethodService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {
    private final Logger logger = LoggerFactory.getLogger(DiaryServiceImpl.class);
    private final DiaryRepository diaryRepository;
    private final MethodService methodService;

    @Transactional
    public void writeDiary(DiaryInsert diaryInsert, UserDetails auth) {
        logger.info("DiaryServiceImpl : writeDiary() - 일기 작성을 시작합니다.");
        logger.debug(auth.getID() + "auth ID값 확인");
        User user = methodService.getUserById(auth.getID());
        Optional<Diary> existingDiary = diaryRepository.findByUserAndDateAndTitle(user, diaryInsert.getDate(), diaryInsert.getTitle());
        Diary diary;
        if (existingDiary.isPresent()){
            logger.warn("DiaryServiceImpl : writeDiary() - 이미 존재하는 일기입니다.");
            throw new IllegalArgumentException("이미 존재하는 일기입니다.");
        }
        else {
            logger.info("DiaryServiceImpl : writeDiary() - 새로운 일기를 작성합니다.");
            diary = Diary.builder()
                    .title(diaryInsert.getTitle())
                    .content(diaryInsert.getContent())
                    .feelings(Collections.singletonList(diaryInsert.getFeeling()))
                    .weathers(Collections.singletonList((diaryInsert.getWeather())))
                    .pictures(diaryInsert.getPictures())
                    .date(diaryInsert.getDate())
                    .user(user)
                    .build();
        }
        diaryRepository.save(diary);
    }

    public List<DiarySelected> getDiary(String date, UserDetails auth){
        logger.info("DiaryServiceImpl : getDiary() - 일기 조회를 시작합니다.");
        User user = methodService.getUserById(auth.getID());
        List<Diary> diarys = diaryRepository.findByUserAndDate(user, date);
        if (diarys.isEmpty()) {
            logger.warn("DiaryServiceImpl : getDiary() - 해당 날짜의 일기가 존재하지 않습니다.");
            throw new IllegalArgumentException("해당 날짜의 일기가 존재하지 않습니다.");
        }
        List<DiarySelected> diarySelecteds = new ArrayList<>();
        addDiarySelected(diarySelecteds, diarys, user);

        logger.info("DiaryServiceImpl : getDiary() - 일기 조회가 완료되었습니다.");
        return diarySelecteds;
    }

    public List<DiarySelected> getAllDiary(UserDetails auth){
       logger.info("DiaryServiceImpl : getAllDiary() - 모든 일기 조회를 시작합니다.");
        User user = methodService.getUserById(auth.getID());
        List<Diary> diarys = diaryRepository.findByUser(user);
        if (diarys.isEmpty()) {
            logger.warn("DiaryServiceImpl : getAllDiary() - 해당 유저의 일기가 존재하지 않습니다.");
            throw new IllegalArgumentException("해당 유저의 일기가 존재하지 않습니다.");
        }
        List<DiarySelected> diarySelecteds = new ArrayList<>();
        addDiarySelected(diarySelecteds, diarys, user);
        logger.info("DiaryServiceImpl : getAllDiary() - 모든 일기 조회가 완료되었습니다.");
        return diarySelecteds;
    }

    @Transactional
    public void updateDiary(DiaryUpdate diaryUpdate, UserDetails auth){
        logger.info("DiaryServiceImpl : updateDiary() - 일기 수정을 시작합니다.");
        User user = methodService.getUserById(auth.getID());
        Diary diary = diaryRepository.findByUserAndDateAndTitle(user, diaryUpdate.getDate(), diaryUpdate.getTitle()).orElseThrow(()-> new IllegalArgumentException("해당 일기가 존재하지 않습니다."));
        Diary updatedDiary = diary.toBuilder()
                .title(diaryUpdate.getChange())
                .content(diaryUpdate.getContent())
                .feelings(Collections.singletonList(diaryUpdate.getFeeling()))
                .weathers(Collections.singletonList(diaryUpdate.getWeather()))
                .pictures(diaryUpdate.getPictures())
                .build();
        diaryRepository.save(updatedDiary);
        logger.info("DiaryServiceImpl : updateDiary() - 일기 수정이 완료되었습니다.");
    }

    @Transactional
    public void deleteDiary(DiaryDelete diaryDelete, UserDetails auth) {
        logger.info("DiaryServiceImpl : deleteDiary() - 일기 삭제를 시작합니다.");
        User user = methodService.getUserById(auth.getID());
        Diary diary = diaryRepository.findByUserAndDateAndTitle(user, diaryDelete.getDate(), diaryDelete.getTitle())
                .orElseThrow(() -> new IllegalArgumentException("해당 일기가 존재하지 않습니다."));
        diaryRepository.delete(diary);
        logger.info("DiaryServiceImpl : deleteDiary() - 일기 삭제가 완료되었습니다.");
    }

    public DiarySelected getDiaryDetail(Long diaryId, UserDetails auth) {
        logger.info("DiaryServiceImpl : getDiaryDetail() - 일기 상세 조회를 시작합니다.");
        User user = methodService.getUserById(auth.getID());
        Diary diary = diaryRepository.findByIdAndUser(diaryId, user)
                .orElseThrow(() -> new IllegalArgumentException("해당 일기가 존재하지 않습니다."));

        DiarySelected diarySelected = DiarySelected.builder()
                .id(diary.getId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .feeling(diary.getFeelings())
                .weather(diary.getWeathers())
                .pictures(diary.getPictures())
                .date(diary.getDate())
                .userID(user.getUId())
                .build();

        logger.info("DiaryServiceImpl : getDiaryDetail() - 일기 상세 조회가 완료되었습니다.");
        return diarySelected;
    }

    private void addDiarySelected(List<DiarySelected> diarySelecteds, List<Diary> diarys, User user) {
        for (Diary diary : diarys) {
            DiarySelected diarySelected = DiarySelected.builder()
                    .id(diary.getId())
                    .title(diary.getTitle())
                    .content(diary.getContent())
                    .feeling(diary.getFeelings())
                    .weather(diary.getWeathers())
                    .pictures(diary.getPictures())
                    .date(diary.getDate())
                    .userID(user.getUId())
                    .build();
            diarySelecteds.add(diarySelected);
        }
    }
}
