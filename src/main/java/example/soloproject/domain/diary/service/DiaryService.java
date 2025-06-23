package example.soloproject.domain.diary.service;

import example.soloproject.domain.diary.presentation.dto.DiaryInsert;

public interface DiaryService {
    void writeDiary(DiaryInsert diaryInsert);
}
