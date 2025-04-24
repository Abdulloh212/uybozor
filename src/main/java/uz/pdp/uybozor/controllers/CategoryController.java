package uz.pdp.uybozor.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.uybozor.entities.Category;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @GetMapping
    public HttpEntity<?> category() {
        return ResponseEntity.ok(List.of(Category.HOUSE,Category.APARTAMENT,Category.LAND));
    }
}
