package uz.pdp.uybozor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.uybozor.entities.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}