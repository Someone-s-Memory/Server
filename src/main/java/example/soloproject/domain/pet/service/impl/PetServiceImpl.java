package example.soloproject.domain.pet.service.impl;

import example.soloproject.domain.pet.entity.Pet;
import example.soloproject.domain.pet.presentation.dto.PetDto;
import example.soloproject.domain.pet.repository.PetRepository;
import example.soloproject.domain.pet.service.PetService;
import example.soloproject.global.entity.User;
import example.soloproject.global.entity.UserDetails;
import example.soloproject.global.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(PetServiceImpl.class);


    public List<PetDto> getPets(UserDetails auth) {
        logger.info("PetServiceImpl : getPets() - 펫 정보 조회를 시작합니다.");
        User user = userRepository.findByUId(auth.getUId());
        List<Pet> pets = petRepository.findByUser(user);
        logger.debug("PetServiceImpl : getPets() - 조회된 펫 수: " + pets.size());

        return pets.stream().map(pet -> PetDto.builder()
                .name(pet.getName())
                .level(pet.getLevel())
                .experience(pet.getExperience())
                .background(pet.getDecorations().getFirst().getBackground())
                .top(pet.getDecorations().getFirst().getTop())
                .bottom(pet.getDecorations().getFirst().getBottom())
                .build()).toList();
    }
}
