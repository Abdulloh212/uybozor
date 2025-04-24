package uz.pdp.uybozor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.uybozor.entities.Report;

public interface ReportRepository extends JpaRepository<Report, Integer> {
}