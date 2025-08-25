package example.soloproject.domain.shop.service.impl;

import example.soloproject.domain.shop.enitty.Shop;
import example.soloproject.domain.shop.presentation.dto.ExhibitionDto;
import example.soloproject.domain.shop.presentation.dto.ShopDto;
import example.soloproject.domain.shop.repository.ShopRepository;
import example.soloproject.domain.shop.service.ShopService;
import example.soloproject.global.entity.UserDetails;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);
    private final ShopRepository shopRepository;

    public List<ShopDto> getShop() {
        logger.info("ShopServiceImpl : getShop() - 상점 정보 조회 요청이 들어왔습니다.");
        List<Shop> shopList = shopRepository.findAll();
        List<ShopDto> response = new ArrayList<>();
        for(Shop shop : shopList) {
            logger.info("ShopServiceImpl : getShop() - 상점 정보 수정 중...");
            response.add(ShopDto.builder()
                    .id(shop.getId())
                    .name(shop.getName())
                    .price(shop.getPrice())
                    .description(shop.getDescription())
                    .category(shop.getCategory())
                    .build());
        }
        logger.info("ShopServiceImpl : getShop() - 상점 정보 조회가 완료되었습니다.");
        return response;
    }

    public void exhibition(UserDetails auth, List<ExhibitionDto> requests) {
        logger.info("ShopServiceImpl : exhibition() - 상점 진열 요청이 들어왔습니다.");
        for (ExhibitionDto request : requests) {
            Shop shop = Shop.builder()
                    .price(request.getPrice())
                    .name(request.getName())
                    .description(request.getDescription())
                    .category(request.getCategory())
                    .build();
            shopRepository.save(shop);
        }
        logger.info("ShopServiceImpl : exhibition() - 상점 진열이 완료되었습니다.");
    }
}
