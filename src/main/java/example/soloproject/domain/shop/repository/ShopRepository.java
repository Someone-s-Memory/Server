package example.soloproject.domain.shop.repository;

import example.soloproject.domain.shop.enitty.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {

}
