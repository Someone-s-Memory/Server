package example.soloproject.domain.pet.repository;

import example.soloproject.domain.pet.entity.Decoration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DecorationRepository extends JpaRepository<Decoration, Long> {
}
