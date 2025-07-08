package example.soloproject.domain.diary.service;

import example.soloproject.domain.diary.presentation.dto.request.DiaryInsert;
import example.soloproject.domain.diary.presentation.dto.request.DiaryUpdate;
import example.soloproject.domain.diary.presentation.dto.response.DiaryMonth;
import example.soloproject.domain.diary.presentation.dto.response.DiarySelected;
import example.soloproject.global.entity.UserDetails;

import java.util.List;

public interface DiaryService {
    void writeDiary(DiaryInsert diaryInsert, UserDetails auth);
    List<DiarySelected> getDiary(String date, UserDetails auth);
    List<DiarySelected> getAllDiary(UserDetails auth);
    void updateDiary(DiaryUpdate diaryUpdate,UserDetails auth);
    void deleteDiary(String date, String title, UserDetails auth);
    DiarySelected getDiaryDetail(Long diaryId, UserDetails auth);
    DiaryMonth getDiaryMonth(String date, UserDetails auth);
}
