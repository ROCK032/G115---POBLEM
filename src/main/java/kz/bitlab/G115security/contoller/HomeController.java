package kz.bitlab.G115security.contoller;

import kz.bitlab.G115security.entity.Iteam;
import kz.bitlab.G115security.service.ItemService;
import kz.bitlab.G115security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class HomeController {
  private final UserService userService;
  private final ItemService itemService;
  @GetMapping("/")
  @PreAuthorize("isAuthenticated()")
  public String homePage() {
    return "home";
  }

  @PreAuthorize("isAnonymous()")
  @GetMapping("/sign-in")
  public String signInPage() {
    return "sign-in";
  }

  @GetMapping("/forbidden")
  public String forbiddenPage() {
    return "forbidden";
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @GetMapping("/admin-panel")
  public String adminPanel() {
    return "admin-panel";
  }

  @PostMapping("/sing-up")
  @PreAuthorize("isAnonymous()")
  public String singUp(@RequestParam String email,
                       @RequestParam String password,
                       @RequestParam String rePassword,
                       @RequestParam String fullname) {
    var result = userService.createUser(email, password, rePassword, fullname);
    return "redirect:/sign-in?" + result;
  }

  @GetMapping("/profile")
  @PreAuthorize("isAuthenticated()")
  public String profilePage() {
    return "profile";
  }

  @PostMapping("/change-password")
  @PreAuthorize("isAuthenticated()")
  public String changePassword(@RequestParam String currentPassword,
                               @RequestParam String newPassword,
                               @RequestParam String reNewPassword){
    var result = userService.changePassword(currentPassword, newPassword, reNewPassword);
    return "redirect:/profile?"+result;
  }
  @RequestMapping("/details/{itemId}")
  public String detailsPage(@PathVariable("itemId") Long itemId, Model model) {
    // Получите объект item по его идентификатору и добавьте его в модель
    Iteam item = itemService.getItemById(itemId);
    model.addAttribute("item", item);
    return "details"; // Вернуть имя шаблона Thymeleaf
  }
  @GetMapping("/card")
  public String cardPage(){
    return "card";
  }
  @GetMapping("/about")
  public String aboutPage(){
    return "about";
  }
}
