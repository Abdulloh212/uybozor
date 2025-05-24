package uz.pdp.uybozor.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.uybozor.entities.BaseEntity.BaseEntity;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Review extends BaseEntity {
    private String message;
    @ManyToOne
    private Users from;
    @JsonIgnore
    @ManyToOne
    private Post post;
    private Date date=new Date();
}
