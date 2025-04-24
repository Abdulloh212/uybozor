package uz.pdp.uybozor.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import uz.pdp.uybozor.DTO.CheckEmailDto;
import uz.pdp.uybozor.DTO.LoginDTO;
import uz.pdp.uybozor.DTO.RegisterDto;
import uz.pdp.uybozor.DTO.ResetPasswordDto;
import uz.pdp.uybozor.entities.RoleName;
import uz.pdp.uybozor.entities.Users;
import uz.pdp.uybozor.repo.AttachmentRepository;
import uz.pdp.uybozor.repo.RoleRepository;
import uz.pdp.uybozor.repo.UsersRepository;
import uz.pdp.uybozor.servises.JwtService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UsersRepository usersRepository;
    private final AttachmentRepository attachmentRepository;

    public AuthController(JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RoleRepository roleRepository,
                          UsersRepository usersRepository, AttachmentRepository attachmentRepository) {
            this.jwtService = jwtService;
            this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.usersRepository = usersRepository;
        this.attachmentRepository = attachmentRepository;
    }

        @PostMapping("/login")
       public String login(@RequestBody LoginDTO request) {
            try {
                var authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
                Authentication authenticate = authenticationManager.authenticate(authenticationToken);
                String token = jwtService.generateToken((Users) authenticate.getPrincipal());
                return token;
            } catch (AuthenticationException e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
            }
        }
        @PostMapping("/register")
       public HttpEntity<?> register(@RequestBody RegisterDto request) {
            Users user = new Users();
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setNickname(request.getUsername());
            user.setTelephone(request.getTelephone());
            user.setPhoto(attachmentRepository.findById(request.getAttachmentId()).orElseThrow());
            user.setRoles(List.of(roleRepository.findByRoleName(RoleName.ROLE_USER).get()));
            usersRepository.save(user);
            System.out.println(user);
            return ResponseEntity.ok(user);
        }
    @PostMapping("/isSigned")
    public ResponseEntity<Boolean> isSigned(@RequestParam String email) {
        Optional<Users> byEmail = usersRepository.findByEmail(email);
        return ResponseEntity.ok(byEmail.isPresent());
    }


    @PostMapping("/check-email")
       public ResponseEntity<?> checkEmail(@RequestBody CheckEmailDto request) {
        Optional<Users> byEmail = usersRepository.findByEmail(request.getEmail());
        if (byEmail.isPresent()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
        @PostMapping("/reset-password")
       public HttpEntity<?> resetPassword(@RequestBody ResetPasswordDto request) {
            Optional<Users> byEmail = usersRepository.findByEmail(request.getEmail());
            if (byEmail.isPresent()) {
                Users users = byEmail.get();
                users.setPassword(passwordEncoder.encode(request.getPassword()));
                usersRepository.save(users);
                return ResponseEntity.ok(users);
            }else {
                return ResponseEntity.notFound().build();
            }
        }

}
