package example.soloproject.domain.item.enitty;

import example.soloproject.domain.shop.enitty.Shop;
import example.soloproject.global.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
//사람이 가진 아이템 (인벤토리)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    private Shop shop;
    @ManyToOne
    private User user;
}
