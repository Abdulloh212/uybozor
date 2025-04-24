package uz.pdp.uybozor.DTO;

import lombok.Value;

@Value
public class RegisterDto {
    Integer attachmentId;
    String username;
    String telephone;
    String email;
    String password;
}
