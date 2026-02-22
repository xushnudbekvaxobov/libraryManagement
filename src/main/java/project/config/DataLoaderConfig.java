package project.config;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import project.entity.UserEntity;
import project.enums.UserRole;
import project.enums.UserStatus;
import project.repository.UserRepository;

@Component
@Slf4j
public class DataLoaderConfig implements CommandLineRunner {


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${spring.sql.init.mode}")
    private String sqlInitMode;

    public DataLoaderConfig(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void run(String @NonNull ... args) throws Exception {
        if(sqlInitMode.equals("always")){
            UserEntity admin = new UserEntity();
            admin.setName("Xushnudbek");
            admin.setSurname("Vaxobov");
            admin.setEmail("xushnudbekvaxobov0421@gmail.com");
            admin.setPhone("901234567");
            admin.setUsername("admin");
            admin.setPassword(bCryptPasswordEncoder.encode("12345"));
            admin.setRole(UserRole.ROLE_ADMIN);
            admin.setStatus(UserStatus.ACTIVE);
            log.info("Admin user created: {}", admin.getUsername());
            userRepository.save(admin);
        }
    }
}
