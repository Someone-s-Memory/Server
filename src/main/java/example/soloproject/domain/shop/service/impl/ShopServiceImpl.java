package example.soloproject.domain.shop.service.impl;

import example.soloproject.domain.shop.repository.ShopRepository;
import example.soloproject.domain.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);
    private final ShopRepository shopRepository;


}
