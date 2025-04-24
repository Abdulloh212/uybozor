package uz.pdp.uybozor.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.uybozor.entities.BaseEntity.BaseEntity;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Report extends BaseEntity {
    @ManyToOne
    private Users reportedUser;
    @ManyToOne
    private Post post;
    private String message;
    private Date date;
    @ManyToOne
    private Users reportedBy;
}
