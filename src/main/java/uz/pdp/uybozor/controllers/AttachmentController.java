package uz.pdp.uybozor.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.uybozor.entities.Attachment;
import uz.pdp.uybozor.entities.AttachmentContent;
import uz.pdp.uybozor.repo.AttachmentContentRepository;
import uz.pdp.uybozor.repo.AttachmentRepository;

import java.io.IOException;

@RestController
@RequestMapping("/api/file")
public class AttachmentController {

    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;

    public AttachmentController(AttachmentRepository attachmentRepository, AttachmentContentRepository attachmentContentRepository) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentContentRepository = attachmentContentRepository;
    }

    @PostMapping
    public Integer upload(@RequestParam MultipartFile file) throws IOException {
     Attachment attachment = Attachment.builder()
             .fileName(file.getOriginalFilename())
             .build();
     attachmentRepository.save(attachment);

        AttachmentContent attachmentContent = AttachmentContent.builder()
             .attachment(attachment)
             .content(file.getBytes())
             .build();

     attachmentContentRepository.save(attachmentContent);

     return attachment.getId();
     }

    @GetMapping("/{attachmentId}")
    public void getFile(@PathVariable Integer attachmentId, HttpServletResponse response) throws IOException {
        AttachmentContent byAttachmentId = attachmentContentRepository.findByAttachmentId(attachmentId);
        response.getOutputStream().write(byAttachmentId.getContent());
    }



}

