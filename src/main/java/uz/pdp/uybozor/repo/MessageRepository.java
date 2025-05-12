package uz.pdp.uybozor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.uybozor.entities.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByFromIdAndToIdOrFromIdAndToId(Integer from1, Integer to1, Integer from2, Integer to2);

}