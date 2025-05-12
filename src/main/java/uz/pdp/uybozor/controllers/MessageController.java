package uz.pdp.uybozor.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.uybozor.DTO.MessageDTO;
import uz.pdp.uybozor.entities.Message;
import uz.pdp.uybozor.servises.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MessageDTO dto) {
        return ResponseEntity.ok(messageService.createMessage(dto));
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(messageService.getMessage(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody MessageDTO dto) {
        return ResponseEntity.ok(messageService.updateMessage(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/messages/{from}/{to}")
    public ResponseEntity<?> getFromTo(@PathVariable Integer from, @PathVariable Integer to) {
        List<Message> messages = messageService.getMessagesBetween(from, to);
        return ResponseEntity.ok(messages);
    }
}

