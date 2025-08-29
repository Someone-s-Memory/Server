package example.soloproject.domain.pet.repository;

import example.soloproject.domain.pet.entity.Pet;
import example.soloproject.global.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByUser(User user);
}
