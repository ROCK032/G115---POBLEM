package kz.bitlab.G115security.service;

import kz.bitlab.G115security.entity.Iteam;
import kz.bitlab.G115security.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    public List<Iteam> getItems(){
        return itemRepository.findAll();
    }

    public Iteam addItem(Iteam iteam) {
        return itemRepository.save(iteam);
    }

    public Iteam editItem(Iteam iteam) {
        return itemRepository.save(iteam);
    }

    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

    public Iteam getItemById(Long id) {
        return itemRepository.findById(id).orElseThrow();
    }

    public List<Iteam> getFilteredItems(String search) {
        return itemRepository.findFilteredItems(search);
    }
}
