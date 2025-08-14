package example.soloproject.global.repository;

import example.soloproject.global.entity.Calendar;
import example.soloproject.global.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    Optional<Calendar> findByUser(User user);
    void deleteByUser(User user);
}
