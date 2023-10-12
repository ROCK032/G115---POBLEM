package kz.bitlab.G115security.service;

import kz.bitlab.G115security.entity.User;
import kz.bitlab.G115security.repository.RoleRepository;
import kz.bitlab.G115security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final RoleService roleService;

  public User getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public String createUser(String email, String password, String rePassword, String fullname) {
    var user = getUserByEmail(email);
    if (user != null) {
      return "emailError";
    }
    if (!password.equals(rePassword)) {
      return "passwordError";
    }
    var newUser = User.builder()
            .email(email)
            .password(passwordEncoder.encode(password))
            .fullName(fullname)
            .roles(Collections.singletonList(roleService.getRoleUser())) // Используйте Collections.singletonList для создания списка с одним элементом
            .build();
    userRepository.save(newUser);
    return "success";
  }

  public String changePassword(String currentPassword, String newPassword, String reNewPassword) {
    if (!passwordEncoder.matches(currentPassword, getCurrentUser().getPassword())){
      return "errorCurrentPassword";
    }
    if (!newPassword.equals(reNewPassword)){
      return "errorPasswordsNotEqual";
    }
    getCurrentUser().setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(getCurrentUser());
    return "success";
  }

  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof User) {
      return (User) authentication.getPrincipal();
    }
    return null; // Вернуть null, если текущий пользователь не найден
  }

}
