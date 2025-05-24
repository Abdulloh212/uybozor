package uz.pdp.uybozor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.uybozor.entities.Role;
import uz.pdp.uybozor.entities.RoleName;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(RoleName roleName);


}