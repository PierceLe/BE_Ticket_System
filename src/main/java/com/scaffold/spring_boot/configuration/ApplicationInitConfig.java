package com.scaffold.spring_boot.configuration;

import com.scaffold.spring_boot.entity.Unit;
import com.scaffold.spring_boot.entity.Users;
import com.scaffold.spring_boot.enums.Role;
import com.scaffold.spring_boot.repository.UnitRepository;
import com.scaffold.spring_boot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {
    private final PasswordEncoder passwordEncoder;
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, UnitRepository unitRepository) {
        return args -> {
            if (!unitRepository.existsById(1)) {
                Unit units = Unit.builder()
                        .id(1)
                        .name("Director")
                        .build();
                unitRepository.save(units);
            }
            if (!userRepository.existsByUsername("admin")) {
                Users user = Users.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("hale0087"))
                        .role(Role.ADMIN.name())
                        .createdAt(LocalDate.now())
                        .dob(LocalDate.of(2005, 1, 15))
                        .email("hale0087@uni.sydney.edu.au")
                        .description("Im the boss of this system")
                        .fullName("Pierce Le")
                        .unitId(1)
                        .build();
                userRepository.save(user);
                log.warn("admin user has been created with private password in dotenv files");
            }
        };
    }
}
