package uz.pdp.uybozor.controllers;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.uybozor.entities.Attachment;
import uz.pdp.uybozor.entities.AttachmentContent;
import uz.pdp.uybozor.entities.Users;
import uz.pdp.uybozor.repo.AttachmentContentRepository;
import uz.pdp.uybozor.repo.AttachmentRepository;
import uz.pdp.uybozor.servises.JwtService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/file")
public class AttachmentController {

    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;
    private final JwtService jwtService;

    public AttachmentController(AttachmentRepository attachmentRepository, AttachmentContentRepository attachmentContentRepository, JwtService jwtService) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentContentRepository = attachmentContentRepository;
        this.jwtService = jwtService;
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
    @PostMapping("/many")
    public List<Integer> uploadFiles(@RequestParam("files") List<MultipartFile> files) throws IOException {
        List<Integer> attachmentIds = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }

            Attachment attachment = Attachment.builder()
                    .fileName(file.getOriginalFilename())
                    .build();
            attachmentRepository.save(attachment);

            AttachmentContent attachmentContent = AttachmentContent.builder()
                    .attachment(attachment)
                    .content(file.getBytes())
                    .build();
            attachmentContentRepository.save(attachmentContent);

            attachmentIds.add(attachment.getId());
        }

        return attachmentIds;
    }

    @GetMapping("/{attachmentId}")
    public void getFile(@PathVariable Integer attachmentId, HttpServletResponse response) throws IOException {
        AttachmentContent byAttachmentId = attachmentContentRepository.findByAttachmentId(attachmentId);
        response.getOutputStream().write(byAttachmentId.getContent());
    }


    }