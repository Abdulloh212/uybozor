package uz.pdp.uybozor.DTO;

import lombok.Value;

@Value
public class ReviewDTO {
     String message;
     Integer fromUserId;
     Integer postId;
}

