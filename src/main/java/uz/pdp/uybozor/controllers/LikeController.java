package uz.pdp.uybozor.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.uybozor.entities.Post;
import uz.pdp.uybozor.entities.Users;
import uz.pdp.uybozor.repo.PostRepository;
import uz.pdp.uybozor.repo.UsersRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/like")
public class LikeController {
    private final UsersRepository usersRepository;
    private final PostRepository postRepository;

    public LikeController(UsersRepository usersRepository, PostRepository postRepository) {
        this.usersRepository = usersRepository;
        this.postRepository = postRepository;
    }

    @GetMapping
    public HttpEntity<?> like(@RequestParam Integer userId, @RequestParam Integer postId) {
       if (userId != null) {
           Optional<Users> byId = usersRepository.findById(userId);
           if (byId.isPresent()) {
               Users user = byId.get();
               List<Post> likedPosts = user.getLikedPosts();
               if (likedPosts != null) {
                   Optional<Post> byId1 = postRepository.findById(postId);
                   if (byId1.isPresent()) {
                       likedPosts.add(byId1.get());
                       user.setLikedPosts(likedPosts);
                       usersRepository.save(user);
                       return ResponseEntity.ok().build();
                   }
               }
           }else {
               return ResponseEntity.notFound().build();
           }
       }
       return ResponseEntity.notFound().build();
    }
}
