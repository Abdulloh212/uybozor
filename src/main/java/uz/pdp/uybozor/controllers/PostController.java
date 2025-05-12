package uz.pdp.uybozor.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.uybozor.DTO.PostDTO;
import uz.pdp.uybozor.entities.Category;
import uz.pdp.uybozor.entities.Post;
import uz.pdp.uybozor.entities.Status;
import uz.pdp.uybozor.servises.PostService;
import uz.pdp.uybozor.specifications.PostSpecification;


@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PostDTO dto) {
        return ResponseEntity.ok(postService.createPost(dto));
    }

    @GetMapping()
    public ResponseEntity<?> getPosts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Double sum,
            @RequestParam(required = false) String locationOnTxt,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable =  PageRequest.of(page, size);

        Specification<Post> spec = Specification.where(PostSpecification.hasTitle(title))
                .and(PostSpecification.hasAuthor(author))
                .and(PostSpecification.hasSum(sum))
                .and(PostSpecification.hasLocation(locationOnTxt))
                .and(PostSpecification.hasCategory(category))
                .and(PostSpecification.hasStatus(status));

        Page<Post> postsPage = postService.findPosts(spec, pageable);
        return ResponseEntity.ok(postsPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody PostDTO dto) {
        return ResponseEntity.ok(postService.updatePost(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
