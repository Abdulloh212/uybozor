package uz.pdp.uybozor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.uybozor.entities.Attachment;
import uz.pdp.uybozor.entities.Role;
import uz.pdp.uybozor.entities.RoleName;
import uz.pdp.uybozor.entities.Users;
import uz.pdp.uybozor.repo.AttachmentRepository;
import uz.pdp.uybozor.repo.RoleRepository;
import uz.pdp.uybozor.repo.UsersRepository;

import java.util.List;
@Component
public class Run implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String isHad;
    @Autowired
    private AttachmentRepository attachmentRepository;


    @Override
    public void run(String... args) {
        if (isHad.equals("create")) {
        Role role1 =Role.builder()
                .roleName(RoleName.ROLE_ADMIN)
                .build();
        Role role2 =Role.builder()
                .roleName(RoleName.ROLE_USER)
                .build();
        roleRepository.save(role2);
        roleRepository.save(role1);
        Attachment attachment=new Attachment(null);
            attachmentRepository.save(attachment);
            Users user = new Users();
            user.setEmail("masturabonu.1985@gmail.com");
            user.setPassword(passwordEncoder.encode("123123"));
            user.setTelephone("994095956");
            user.setNickname("Abdulloh");
            user.setPhoto(attachment);
            user.setRoles(List.of(role1, role2));

            usersRepository.save(user);
        }
    }
}
