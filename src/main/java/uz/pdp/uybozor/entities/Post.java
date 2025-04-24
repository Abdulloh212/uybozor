package uz.pdp.uybozor.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.uybozor.entities.BaseEntity.BaseEntity;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Post extends BaseEntity {
    private String title;

    @ManyToOne
    private Users author;

    private Double sum;
    private String locationOnTxt;
    private Date date=new Date();
    private String description;

    @OneToMany
    private List<Attachment> photos;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @OneToMany
    private List<Review> reviews;
    @ManyToOne
    private Location location;
}
