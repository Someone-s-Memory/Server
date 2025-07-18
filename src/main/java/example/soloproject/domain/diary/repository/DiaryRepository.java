package example.soloproject.domain.diary.repository;

import example.soloproject.domain.diary.entity.Diary;
import example.soloproject.global.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Optional<Diary> findByUserAndDateAndTitle(User user, String date, String title);
    List<Diary> findByUserAndDate(User user, String date);
    List<Diary> findByUser(User user);
    Optional<Diary> findByIdAndUser(Long id, User user);
    @EntityGraph(attributePaths = {"feelings"})
    @Query("SELECT d FROM Diary d WHERE d.user = :user AND LEFT(d.date,7) = :date")
    List<Diary> findByUserAndDateContaining(User user, String date);
}
