package example.soloproject.domain.diary.service;

import example.soloproject.domain.diary.presentation.dto.request.DiaryInsert;
import example.soloproject.global.entity.UserDetails;

public interface DiaryService {
    void writeDiary(DiaryInsert diaryInsert, UserDetails auth);
}
