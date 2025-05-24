package uz.pdp.uybozor.servises;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.uybozor.DTO.ReviewDTO;
import uz.pdp.uybozor.entities.Post;
import uz.pdp.uybozor.entities.Review;
import uz.pdp.uybozor.entities.Users;
import uz.pdp.uybozor.repo.PostRepository;
import uz.pdp.uybozor.repo.ReviewRepository;
import uz.pdp.uybozor.repo.UsersRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UsersRepository usersRepository;
    private final PostRepository postRepository;

    public Review createReview(ReviewDTO dto) {
        Users from = usersRepository.findById(dto.getFromUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Review review = new Review();
        review.setMessage(dto.getMessage());
        review.setFrom(from);
        review.setPost(post);
        review.setDate(new Date());
        Review save = reviewRepository.save(review);
        List<Review> reviews = post.getReviews();
        reviews.add(save);
        post.setReviews(reviews);
        postRepository.save(post);
        return save;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReview(Integer id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    public Review updateReview(Integer id, ReviewDTO dto) {
        Review review = getReview(id);
        review.setMessage(dto.getMessage());

        if (dto.getFromUserId() != null) {
            Users from = usersRepository.findById(dto.getFromUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            review.setFrom(from);
        }

        if (dto.getPostId() != null) {
            Post post = postRepository.findById(dto.getPostId())
                    .orElseThrow(() -> new RuntimeException("Post not found"));
            review.setPost(post);
        }

        return reviewRepository.save(review);
    }

    public void deleteReview(Integer id) {
        reviewRepository.deleteById(id);
    }
}

