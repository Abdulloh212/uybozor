package uz.pdp.uybozor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.uybozor.entities.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
}