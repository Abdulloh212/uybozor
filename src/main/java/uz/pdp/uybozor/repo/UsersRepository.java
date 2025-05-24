package uz.pdp.uybozor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.uybozor.entities.Users;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);

    void deleteUsersByEmail(String mail);
}
