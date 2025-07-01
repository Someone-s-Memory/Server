package example.soloproject.domain.diary.service.impl;

import example.soloproject.domain.diary.entity.Diary;
import example.soloproject.domain.diary.presentation.dto.request.DiaryInsert;
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

import java.util.Collections;

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

        Diary diary = Diary.builder()
                .title(diaryInsert.getTitle())
                .content(diaryInsert.getContent())
                .feelings(Collections.singletonList(diaryInsert.getFeeling()))
                .weathers(Collections.singletonList((diaryInsert.getWeather())))
                .pictures(diaryInsert.getPictures())
                .date(diaryInsert.getDate())
                .user(user)
                .build();
        diaryRepository.save(diary);
    }
}
