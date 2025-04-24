package uz.pdp.uybozor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.uybozor.entities.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
}