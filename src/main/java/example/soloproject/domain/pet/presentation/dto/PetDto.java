package example.soloproject.domain.pet.presentation.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PetDto {
    private String name;
    private int level;
    private double experience;
    private String background;
    private String top;
    private String bottom;
}
