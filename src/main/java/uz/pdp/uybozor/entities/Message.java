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
public class Message extends BaseEntity {
    @ManyToOne
    private Users from;
    @ManyToOne
    private Users to;
    private String text;
    private Date date=new Date();
}
