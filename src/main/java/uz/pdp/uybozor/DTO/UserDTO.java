package uz.pdp.uybozor.DTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Value;
import uz.pdp.uybozor.entities.Attachment;
import uz.pdp.uybozor.entities.Post;

import java.util.List;

@Value
public class UserDTO {
     Integer id;
     String nickname;
     String telephone;
     String email;
     List<Integer> likedPosts;
     Integer photo;
}
