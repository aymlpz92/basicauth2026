package co.simplon.basicauth.init;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import co.simplon.basicauth.entity.RoleEntity;
import co.simplon.basicauth.entity.TodoEntity;
import co.simplon.basicauth.entity.UserEntity;
import co.simplon.basicauth.repository.RoleRepository;
import co.simplon.basicauth.repository.TodoRepository;
import co.simplon.basicauth.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final TodoRepository todoRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityBuilder securityBuilder;

    public DataInitializer(TodoRepository todoRepositoryInjected, RoleRepository roleRepositoryInjected, UserRepository userRepositoryInjected,
            PasswordEncoder passwordEncoderInjected, SecurityBuilder securityBuilder) {
        this.todoRepository = todoRepositoryInjected;
        this.roleRepository = roleRepositoryInjected;
        this.userRepository = userRepositoryInjected;
        this.passwordEncoder = passwordEncoderInjected;
        this.securityBuilder = securityBuilder;
    }

    @Override
    public void run(String... args) throws Exception {
        this.todoRepository.save(new TodoEntity("Clone the project", true));
        this.todoRepository.save(new TodoEntity("Test the API", true));
        this.todoRepository.save(new TodoEntity("Add basic authentication"));

        RoleEntity roleUser = new RoleEntity();
        roleUser.setAuthority("ROLE_USER");
        roleRepository.save(roleUser);

        RoleEntity roleAdmin = new RoleEntity();
        roleAdmin.setAuthority("ROLE_ADMIN");
        roleRepository.save(roleAdmin);

        UserEntity admin = new UserEntity();
        admin.setUsername("admin@example.com");
        admin.setPassword(passwordEncoder.encode("securepassword"));
        admin.setAuthorities(Set.of(roleAdmin));
        userRepository.save(admin);

        UserEntity user = new UserEntity();
        user.setUsername("bastien@example.com");
        user.setPassword("tacostacos");
        user.setAuthorities(Set.of(roleUser));
        userRepository.save(user);

    }

}
