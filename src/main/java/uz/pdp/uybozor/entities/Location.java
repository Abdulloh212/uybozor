package uz.pdp.uybozor.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.uybozor.entities.BaseEntity.BaseEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Location extends BaseEntity {
    private Double latitude;
    private Double longitude;
}
