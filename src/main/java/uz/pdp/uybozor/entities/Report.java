package uz.pdp.uybozor.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
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
    @JoinColumn(name = "reported_user_id")
    private Users reportedUser;

    @ManyToOne
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE"))
    private Post post;

    private String message;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "reported_by_id")
    private Users reportedBy;
}
