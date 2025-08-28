package example.soloproject.domain.shop.presentation.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyDto {
    private Long shopId;
    private Integer quantity;
}
