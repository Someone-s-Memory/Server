package example.soloproject.domain.item.repository;

import example.soloproject.domain.item.enitty.Item;
import example.soloproject.global.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByUser(User user);
}