package example.soloproject.domain.shop.service.impl;

import example.soloproject.domain.item.enitty.Item;
import example.soloproject.domain.item.repository.ItemRepository;
import example.soloproject.domain.shop.enitty.Shop;
import example.soloproject.domain.shop.presentation.dto.BuyDto;
import example.soloproject.domain.shop.presentation.dto.ExhibitionDto;
import example.soloproject.domain.shop.presentation.dto.ShopDto;
import example.soloproject.domain.shop.repository.ShopRepository;
import example.soloproject.domain.shop.service.ShopService;
import example.soloproject.global.entity.User;
import example.soloproject.global.entity.UserDetails;
import example.soloproject.global.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

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
            shopRepository.findByName(request.getName()).ifPresent(shop -> {
                throw new IllegalArgumentException("이미 존재하는 아이템입니다: " + shop.getName());
            });
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

    public void buyItem(BuyDto request, UserDetails auth) {
        logger.info("ShopServiceImpl : buyItem() - 아이템 구매 요청이 들어왔습니다.");
        Shop shop = shopRepository.findById(request.getShopId()).orElseThrow(() -> new IllegalArgumentException("아이템이 존재하지 않습니다."));
        User user = userRepository.findByUId(auth.getUId());
        long price = shop.getPrice() * request.getQuantity();
        if (user.getCoin() < price) {
            throw new IllegalArgumentException("코인이 부족합니다.");
        }
        user.setCoin((int) (user.getCoin() - price));
        userRepository.save(user);
        Item item = Item.builder()
                .quantity(request.getQuantity())
                .shop(shop)
                .user(user)
                .build();
        itemRepository.save(item);
        logger.info("ShopServiceImpl : buyItem() - 아이템 구매가 완료되었습니다.");
    }

    public void sold(UserDetails auth, BuyDto request){
        logger.info("ShopServiceImpl : sold() - 상점 판매 완료 요청이 들어왔습니다.");
        User user = userRepository.findByUId(auth.getUId());
        Item item = itemRepository.findById(request.getShopId()).orElseThrow(() -> new IllegalArgumentException("아이템이 존재하지 않습니다."));
        if (!item.getUser().getUId().equals(user.getUId())) {
            throw new IllegalArgumentException("해당 아이템을 소유하고 있지 않습니다.");
        }
        if (item.getQuantity() < request.getQuantity()) {
            throw new IllegalArgumentException("판매하려는 아이템의 수량이 보유한 수량보다 많습니다.");
        }
        long price = Math.round(item.getShop().getPrice() * request.getQuantity() * 0.8);
        user.setCoin((int) (user.getCoin() + price));
        userRepository.save(user);
        item.setQuantity(item.getQuantity() - request.getQuantity());
        if (item.getQuantity() == 0) {
            itemRepository.delete(item);
        } else {
            itemRepository.save(item);
        }
        logger.info("ShopServiceImpl : sold() - 상점 판매가 완료되었습니다.");
    }
}
