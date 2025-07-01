package example.soloproject.domain.diary.service;

import example.soloproject.domain.diary.presentation.dto.request.DiaryInsert;
import example.soloproject.domain.diary.presentation.dto.request.DiarySelect;
import example.soloproject.domain.diary.presentation.dto.response.DiarySelected;
import example.soloproject.global.entity.UserDetails;

import java.util.List;

public interface DiaryService {
    void writeDiary(DiaryInsert diaryInsert, UserDetails auth);
    List<DiarySelected> getDiary(DiarySelect diarySelect, UserDetails auth);
    List<DiarySelected> getAllDiary(UserDetails auth);
}
