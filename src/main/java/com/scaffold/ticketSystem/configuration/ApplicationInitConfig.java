package com.scaffold.ticketSystem.configuration;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.scaffold.ticketSystem.entity.Unit;
import com.scaffold.ticketSystem.entity.Users;
import com.scaffold.ticketSystem.enums.Role;
import com.scaffold.ticketSystem.repository.UnitRepository;
import com.scaffold.ticketSystem.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {
    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, UnitRepository unitRepository) {
        return args -> {
            if (!unitRepository.existsById(1)) {
                Unit units = Unit.builder().name("Director").build();
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
                        .locked(false)
                        .unitId(1)
                        .activeTickets(0)
                        .build();
                userRepository.save(user);
                log.info("admin user has been created with private password in dotenv files");
            }
            if (!userRepository.existsByUsername("qa")) {
                Users user = Users.builder()
                        .username("qa")
                        .password(passwordEncoder.encode("hale0087"))
                        .role(Role.QA.name())
                        .createdAt(LocalDate.now())
                        .dob(LocalDate.of(2005, 1, 15))
                        .email("haichau.hvsg@gmail.com")
                        .description("Im the QA of this system")
                        .fullName("Hai Chau Le")
                        .locked(false)
                        .unitId(1)
                        .activeTickets(0)
                        .build();
                userRepository.save(user);
                log.info("qa user has been created with private password in dotenv files");
            }
        };
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
        return template;
    }
}
