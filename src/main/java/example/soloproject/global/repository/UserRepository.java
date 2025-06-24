package example.soloproject.global.repository;


import example.soloproject.global.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByID(Long id);
    User findByUId(String uid);
}
