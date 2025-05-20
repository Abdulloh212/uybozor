package uz.pdp.uybozor.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.uybozor.DTO.UserDTO;
import uz.pdp.uybozor.entities.Post;
import uz.pdp.uybozor.entities.Users;
import uz.pdp.uybozor.repo.UsersRepository;
import uz.pdp.uybozor.servises.JwtService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final JwtService jwtService;
    private final UsersRepository usersRepository;

    public UserController(JwtService jwtService, UsersRepository usersRepository) {
        this.jwtService = jwtService;
        this.usersRepository = usersRepository;
    }

    @GetMapping()
    public HttpEntity<?> getUser(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.replace("Bearer ", "");
            Users usernameFromToken = jwtService.getUserFromToken(token);
            List<Integer> liked = new ArrayList<>();
            if (usernameFromToken.getLikedPosts() != null) {
            for (Integer likedPost : usernameFromToken.getLikedPosts()) {
                liked.add(likedPost);
            }
            }
            List<Integer> own = new ArrayList<>();
            UserDTO userDTO = new UserDTO(
                    usernameFromToken.getId(),
                    usernameFromToken.getNickname(),
                    usernameFromToken.getTelephone(),
                    usernameFromToken.getEmail(),
                    liked,
                    usernameFromToken.getPhoto().getId()
            );
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/id")
    public HttpEntity<?> get(@RequestBody Integer id) {
        Users users = usersRepository.findById(id).get();
        List<Integer> liked = new ArrayList<>();
        for (Integer likedPost : users.getLikedPosts()) {
            liked.add(likedPost);
        }
        UserDTO userDTO = new UserDTO(
                users.getId(),
                users.getNickname(),
                users.getTelephone(),
                users.getEmail(),
                liked,
                users.getPhoto().getId()
        );
        return ResponseEntity.ok(userDTO);
}
}