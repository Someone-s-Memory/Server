package example.soloproject.domain.diary.service.impl;

import example.soloproject.domain.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {
    private final Logger logger = LoggerFactory.getLogger(DiaryServiceImpl.class);

    public void writeDiary() {
        logger.info("DiaryServiceImpl : writeDiary() - 일기 작성을 시작합니다.");

    }
}
