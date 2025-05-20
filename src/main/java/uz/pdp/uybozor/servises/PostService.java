package uz.pdp.uybozor.servises;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.pdp.uybozor.DTO.PostDTO;
import uz.pdp.uybozor.entities.*;
import uz.pdp.uybozor.repo.AttachmentRepository;
import uz.pdp.uybozor.repo.LocationRepository;
import uz.pdp.uybozor.repo.PostRepository;
import uz.pdp.uybozor.repo.UsersRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UsersRepository usersRepository;
    private final AttachmentRepository attachmentRepository;
    private final LocationRepository locationRepository;

    public Post createPost(PostDTO dto) {
        Users author = usersRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        List<Attachment> photos = attachmentRepository.findAllById(dto.getPhotoIds());

        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setAuthor(author);
        post.setSum(dto.getSum());
        post.setLocationOnTxt(dto.getLocation());
        post.setDescription(dto.getDescription());
        post.setPhotos(photos);
        post.setCategory(dto.getCategory());
        post.setStatus(Status.ACTIVE);
        post.setDate(new Date());

        Location location = new Location();
        location.setLatitude(dto.getLatitude());
        location.setLongitude(dto.getLongitude());

        locationRepository.save(location);

        post.setLocation(location);

        Post save = postRepository.save(post);
        return save;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPost(Integer id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public Post updatePost(Integer id, PostDTO dto) {
        Post post = getPost(id);

        if (dto.getAuthorId() != null) {
            Users author = usersRepository.findById(dto.getAuthorId())
                    .orElseThrow(() -> new RuntimeException("Author not found"));
            post.setAuthor(author);
        }

        if (dto.getPhotoIds() != null) {
            List<Attachment> photos = attachmentRepository.findAllById(dto.getPhotoIds());
            post.setPhotos(photos);
        }

        post.setTitle(dto.getTitle());
        post.setSum(dto.getSum());
        post.setLocationOnTxt(dto.getLocation());
        post.setDescription(dto.getDescription());
        post.setCategory(dto.getCategory());
        post.setStatus(dto.getStatus());

        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Integer id) {
        List<Users> allUsers = usersRepository.findAll();

        for (Users user : allUsers) {
            List<Integer> likedPostIds = user.getLikedPosts();
            if (likedPostIds != null && likedPostIds.removeIf(postId -> postId.equals(id))) {
                usersRepository.save(user);
            }
        }
        postRepository.deleteById(id);
    }


    public Page<Post> findPosts(Specification<Post> spec, Pageable pageable) {
        return postRepository.findAll(spec, pageable);
    }

    public List<Post> getUsers(Integer id) {
        return postRepository.findAllByAuthor_Id(id);
    }
}

