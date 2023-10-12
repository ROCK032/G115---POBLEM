package kz.bitlab.G115security.contoller;

import kz.bitlab.G115security.entity.Iteam;
import kz.bitlab.G115security.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/items")
public class ItemController {
  private final ItemService itemService;


  @GetMapping
  public List<Iteam> getItems() {
    return itemService.getItems();
  }

  @PostMapping
  public Iteam addItem(@RequestBody Iteam iteam) {
    return itemService.addItem(iteam);
  }

  @PutMapping
  public Iteam editItem(@RequestBody Iteam iteam) {
    return itemService.editItem(iteam);
  }

  @DeleteMapping("{id}")
  public void deleteItem(@PathVariable Long id) {
    itemService.deleteById(id);
  }

  @GetMapping("{id}")
  public Iteam getItem(@PathVariable Long id) {
    return itemService.getItemById(id);
  }
  @GetMapping("/search")
  public List<Iteam> getFilteredItems(@RequestParam String search){
    return itemService.getFilteredItems(search);
  }
    @RequestMapping("/details/{itemId}")
  public String detailsPage(@PathVariable("itemId") Long itemId, Model model) {
    // Получите объект item по его идентификатору и добавьте его в модель
    Iteam item = itemService.getItemById(itemId);
    model.addAttribute("item", item);
    return "details"; // Вернуть имя шаблона Thymeleaf
  }
}
