package example.soloproject.domain.shop.service;

import example.soloproject.domain.shop.presentation.dto.ShopDto;
import example.soloproject.global.entity.UserDetails;

import java.util.List;

public interface ShopService {
    List<ShopDto> getShop();
}
