package uz.pdp.uybozor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.uybozor.entities.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}