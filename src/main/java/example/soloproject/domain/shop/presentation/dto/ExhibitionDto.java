package example.soloproject.domain.shop.presentation.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExhibitionDto {
    private String name;
    private String description;
    private Long price;
    private String category;
}
