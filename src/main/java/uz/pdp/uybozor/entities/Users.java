package uz.pdp.uybozor.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.uybozor.entities.BaseEntity.BaseEntity;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Users extends BaseEntity implements UserDetails {
      private String nickname;
      @JsonIgnore
      private String password;
      private String telephone;
      private String email;
      @ElementCollection
      private List<Integer> likedPosts;
      @ManyToOne
      private Attachment photo;
      @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
      private List<Role>roles;
      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

      @Override
      public String getUsername() {
        return email;
    }
}
