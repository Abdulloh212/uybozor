package uz.pdp.uybozor.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.uybozor.entities.BaseEntity.BaseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class AttachmentContent extends BaseEntity {
    private byte[] content;

    @ManyToOne
    private Attachment attachment;
}
