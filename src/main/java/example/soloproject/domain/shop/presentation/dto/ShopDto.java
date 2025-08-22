package example.soloproject.domain.shop.presentation.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopDto {
    private Long id;
    private String name;
    private Long price;
    private String description;
    private String category;
}
