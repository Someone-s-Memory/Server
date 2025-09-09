package example.soloproject.domain.item.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDto {
    private String name;
    private Long price;
    private String description;
    private String category;
    private Integer quantity;
}
