package uz.pdp.uybozor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.uybozor.entities.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}