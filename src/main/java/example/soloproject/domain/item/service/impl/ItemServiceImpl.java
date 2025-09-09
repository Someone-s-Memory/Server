package example.soloproject.domain.item.service.impl;

import example.soloproject.domain.item.enitty.Item;
import example.soloproject.domain.item.presentation.dto.InventoryDto;
import example.soloproject.domain.item.repository.ItemRepository;
import example.soloproject.domain.item.service.ItemService;
import example.soloproject.global.entity.User;
import example.soloproject.global.entity.UserDetails;
import example.soloproject.global.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public List<InventoryDto> getInventory(UserDetails auth){
        User user = userRepository.findByUId(auth.getUId());
        List<Item> items = itemRepository.findByUser(user);
        List<InventoryDto> inventoryDtos = null;
        for(Item item : items){
            inventoryDtos.add(InventoryDto.builder()
                    .name(item.getShop().getName())
                    .price(item.getShop().getPrice())
                    .description(item.getShop().getDescription())
                    .category(item.getShop().getCategory())
                    .quantity(item.getQuantity())
                    .build());
        }
        return inventoryDtos;
    }

}
