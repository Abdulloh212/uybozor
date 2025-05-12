package uz.pdp.uybozor.servises;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.pdp.uybozor.entities.Role;
import uz.pdp.uybozor.entities.RoleName;
import uz.pdp.uybozor.entities.Users;
import uz.pdp.uybozor.repo.AttachmentRepository;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final AttachmentRepository attachmentRepository;
    @Value("${jwt.secret}")
    private String privateKey;

    private SecretKey secretKey;

    public JwtService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    @PostConstruct
    public void init() {
        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(privateKey));
    }

    public String generateToken(Users user) {
        return "Bearer " + Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
                .signWith(secretKey)
                .claim("id", user.getId())
                .claim("nickname", user.getNickname())
                .claim("telephone", user.getTelephone())
                .claim("email", user.getEmail())
                .claim("roles", user.getRoles())
                .claim("photo", user.getPhoto().getId())
                .compact();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Users getUserFromToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Users user = new Users();
            user.setId(Integer.valueOf(claims.get("id").toString()));
            user.setNickname(claims.get("nickname", String.class));
            user.setTelephone(claims.get("telephone", String.class));
            user.setEmail(claims.get("email", String.class));

            Integer photoId = claims.get("photo", Integer.class);
            if (photoId != null) {
                user.setPhoto(attachmentRepository.findById(photoId).orElse(null));
            }

            List<?> roleMaps = claims.get("roles", List.class);
            List<Role> roles = new ArrayList<>();
            for (Object roleObj : roleMaps) {
                if (roleObj instanceof LinkedHashMap<?, ?> roleMap) {
                    Object roleNameValue = roleMap.get("roleName");
                    if (roleNameValue instanceof String roleNameStr) {
                        Role role = new Role();
                        role.setRoleName(RoleName.valueOf(roleNameStr));
                        roles.add(role);
                    }
                } else if (roleObj instanceof String roleNameStr) {
                    Role role = new Role();
                    role.setRoleName(RoleName.valueOf(roleNameStr));
                    roles.add(role);
                }
            }

            user.setRoles(roles);
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }




}

