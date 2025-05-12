package uz.pdp.uybozor.specifications;

import org.springframework.data.jpa.domain.Specification;
import uz.pdp.uybozor.entities.Category;
import uz.pdp.uybozor.entities.Post;
import uz.pdp.uybozor.entities.Status;

public class PostSpecification {

    public static Specification<Post> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title == null || title.isEmpty()) {
                return criteriaBuilder.conjunction();  // return no filtering if title is null or empty
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
        };
    }

    public static Specification<Post> hasAuthor(String authorName) {
        return (root, query, criteriaBuilder) -> {
            if (authorName == null || authorName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("author").get("nickname")), "%" + authorName.toLowerCase() + "%");
        };
    }

    public static Specification<Post> hasSum(Double sum) {
        return (root, query, criteriaBuilder) -> {
            if (sum == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("sum"), sum);
        };
    }

    public static Specification<Post> hasLocation(String location) {
        return (root, query, criteriaBuilder) -> {
            if (location == null || location.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("locationOnTxt")), "%" + location.toLowerCase() + "%");
        };
    }

    public static Specification<Post> hasCategory(Category category) {
        return (root, query, criteriaBuilder) -> {
            if (category == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("category"), category);
        };
    }

    public static Specification<Post> hasStatus(Status status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

}
