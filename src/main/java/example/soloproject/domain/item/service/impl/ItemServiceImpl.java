package example.soloproject.domain.item.service.impl;

import example.soloproject.domain.item.repository.ItemRepository;
import example.soloproject.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

}
