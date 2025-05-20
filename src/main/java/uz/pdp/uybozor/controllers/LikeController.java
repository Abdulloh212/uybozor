package uz.pdp.uybozor.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.uybozor.entities.Users;
import uz.pdp.uybozor.repo.PostRepository;
import uz.pdp.uybozor.repo.UsersRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LikeController {
    private final UsersRepository usersRepository;

    public LikeController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/like")
    public HttpEntity<?> like(@RequestParam Integer userId, @RequestParam Integer postId) {
        if (userId != null && postId != null) {
            Optional<Users> byId = usersRepository.findById(userId);
            if (byId.isPresent()) {
                Users user = byId.get();
                List<Integer> likedPosts = user.getLikedPosts();
                if (likedPosts != null) {
                    if (!likedPosts.contains(postId)) {
                        likedPosts.add(postId);
                        user.setLikedPosts(likedPosts);
                        usersRepository.save(user);
                        return ResponseEntity.ok("Post liked.");
                    } else {
                        return ResponseEntity.ok("Post already liked.");
                    }
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.badRequest().body("Missing userId or postId");
    }

    @GetMapping("/unlike")
    public HttpEntity<?> unlike(@RequestParam Integer userId, @RequestParam Integer postId) {
        if (userId != null && postId != null) {
            Optional<Users> byId = usersRepository.findById(userId);
            if (byId.isPresent()) {
                Users user = byId.get();
                List<Integer> likedPosts = user.getLikedPosts();
                if (likedPosts != null && likedPosts.contains(postId)) {
                    likedPosts.remove(postId);
                    user.setLikedPosts(likedPosts);
                    usersRepository.save(user);
                    return ResponseEntity.ok("Post unliked.");
                } else {
                    return ResponseEntity.badRequest().body("Post was not liked.");
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.badRequest().body("Missing userId or postId");
    }

    @GetMapping("/getLiked")
    public HttpEntity<?> getLiked(@RequestParam Integer userId) {
       if (userId != null) {
           Optional<Users> byId = usersRepository.findById(userId);
           if (byId.isPresent()) {
               Users user = byId.get();
               return ResponseEntity.ok(user.getLikedPosts());
           }else {
               return ResponseEntity.notFound().build();
           }
       }
       return ResponseEntity.notFound().build();
    }
}
