package uz.pdp.uybozor.DTO;

import lombok.Value;

@Value
public class ReportDTO {
     Integer reportedUserId;
     Integer postId;
     String message;
     Integer reportedById;
}
