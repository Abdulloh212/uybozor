package uz.pdp.uybozor.DTO;

import lombok.Value;
import uz.pdp.uybozor.entities.Category;
import uz.pdp.uybozor.entities.Status;

import java.util.List;

@Value
public class PostDTO {
     String title;
     Integer authorId;
     Double sum;
     String location;
     String description;
     List<Integer> photoIds;
     Category category;
     Status status;
     Double longitude;
     Double latitude;
}

