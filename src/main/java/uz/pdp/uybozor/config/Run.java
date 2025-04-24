package uz.pdp.uybozor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.uybozor.entities.Role;
import uz.pdp.uybozor.entities.RoleName;
import uz.pdp.uybozor.repo.RoleRepository;

@Component
public class Run implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String create;

    public Run(RoleRepository roleRepository,PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (create.equals("create")) {
        Role role = new Role(null, RoleName.ROLE_ADMIN);
        Role role1 = new Role(null, RoleName.ROLE_USER);
        Role save = roleRepository.save(role);
        Role save1 = roleRepository.save(role1);
        }
    }
}
