package example.soloproject.domain.shop.service;

import example.soloproject.domain.shop.presentation.dto.BuyDto;
import example.soloproject.domain.shop.presentation.dto.ExhibitionDto;
import example.soloproject.domain.shop.presentation.dto.ShopDto;
import example.soloproject.global.entity.UserDetails;

import java.util.List;

public interface ShopService {
    List<ShopDto> getShop();
    void exhibition(UserDetails auth, List<ExhibitionDto> request);
    void buyItem(BuyDto request, UserDetails auth);
}
