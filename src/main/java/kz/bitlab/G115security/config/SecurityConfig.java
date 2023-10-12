package kz.bitlab.G115security.config;

import kz.bitlab.G115security.repository.UserRepository;
import kz.bitlab.G115security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // активировать конфигурационный класс секьюрити
@EnableMethodSecurity // активирует разрешения при обращении к эндпоинтам по статусу пользователя
public class SecurityConfig {
  @Autowired
  private UserRepository userRepository;

  @Bean
  public UserDetailsService userDetailsService() {
    return email -> {
      var user = userRepository.findByEmail(email);
      if (user == null) {
        throw new UsernameNotFoundException("User not found!");
      }
      return user;
    };
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    var authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
    authManagerBuilder
        .userDetailsService(userDetailsService())
        .passwordEncoder(passwordEncoder());

    http.exceptionHandling(eh-> eh.accessDeniedPage("/forbidden")); // страница при отсутствии доступа к определенному эндпоинту

    http.csrf(AbstractHttpConfigurer::disable);

    http.formLogin(fl -> fl.loginProcessingUrl("/auth") // происходит сам процесс проверки данных пользователя
        .usernameParameter("email") // email пользователя
        .passwordParameter("password") // password пользователя
        .loginPage("/sign-in") // страница для аутентификации
        .defaultSuccessUrl("/", true) // страница при успешной аутентификации
        .failureUrl("/sign-in?error")); // страница при провальной аутентификации

    http.logout(lg -> lg.logoutUrl("/logout") // выход из аккаунта (удаление из сессии)
        .logoutSuccessUrl("/sign-in")); // страница после выхода из аккаунта

    return http.build();
  }
}
