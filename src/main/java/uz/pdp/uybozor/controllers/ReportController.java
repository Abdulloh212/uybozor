package uz.pdp.uybozor.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.uybozor.DTO.ReportDTO;
import uz.pdp.uybozor.servises.ReportService;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<?> createReport(@RequestBody ReportDTO reportDTO) {
        return ResponseEntity.ok(reportService.createReport(reportDTO));
    }

    @GetMapping
    public ResponseEntity<?> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReport(@PathVariable Integer id) {
        return ResponseEntity.ok(reportService.getReport(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReport(@PathVariable Integer id, @RequestBody ReportDTO reportDTO) {
        return ResponseEntity.ok(reportService.updateReport(id, reportDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable Integer id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
}
