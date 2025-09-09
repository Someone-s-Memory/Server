package example.soloproject.domain.item.service;

import example.soloproject.domain.item.presentation.dto.InventoryDto;
import example.soloproject.global.entity.UserDetails;

import java.util.List;

public interface ItemService {
    List<InventoryDto> getInventory(UserDetails auth);
}
