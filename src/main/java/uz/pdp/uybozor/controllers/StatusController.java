package uz.pdp.uybozor.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.uybozor.entities.Status;

import java.util.List;

@RestController
@RequestMapping("/api/status")
public class StatusController {
    @GetMapping
    public HttpEntity<?> status() {
        return ResponseEntity.ok(List.of(Status.ACTIVE, Status.INACTIVE));
    }
}
