package example.soloproject.domain.diary.service.impl;

import example.soloproject.domain.diary.entity.Diary;
import example.soloproject.domain.diary.presentation.dto.DiaryInsert;
import example.soloproject.domain.diary.repository.DiaryRepository;
import example.soloproject.domain.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {
    private final Logger logger = LoggerFactory.getLogger(DiaryServiceImpl.class);
    private final DiaryRepository diaryRepository;

    public void writeDiary(DiaryInsert diaryInsert) {
        logger.info("DiaryServiceImpl : writeDiary() - 일기 작성을 시작합니다.");

        Diary diary = Diary.builder()
                .title(diaryInsert.getTitle())
                .content(diaryInsert.getContent())
                .fillings(Collections.singletonList(diaryInsert.getFilling()))
                .weathers(Collections.singletonList((diaryInsert.getWeather())))
                .picture(diaryInsert.getPicture())
                .date(diaryInsert.getDate())
                .build();
        diaryRepository.save(diary);
    }
}
