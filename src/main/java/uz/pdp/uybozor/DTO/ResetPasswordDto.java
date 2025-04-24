package uz.pdp.uybozor.DTO;

import lombok.Value;

@Value
public class ResetPasswordDto {
    String email;
    String password;
}
