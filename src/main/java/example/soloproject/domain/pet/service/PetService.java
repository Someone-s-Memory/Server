package example.soloproject.domain.pet.service;

import example.soloproject.domain.pet.presentation.dto.PetDto;
import example.soloproject.global.entity.UserDetails;

import java.util.List;

public interface PetService {
    List<PetDto> getPets(UserDetails auth);
}
