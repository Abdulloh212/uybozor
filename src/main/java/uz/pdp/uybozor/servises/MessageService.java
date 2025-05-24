package uz.pdp.uybozor.servises;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.uybozor.DTO.MessageDTO;
import uz.pdp.uybozor.DTO.User2DTO;
import uz.pdp.uybozor.DTO.UserDTO;
import uz.pdp.uybozor.entities.Message;
import uz.pdp.uybozor.entities.Users;
import uz.pdp.uybozor.repo.MessageRepository;
import uz.pdp.uybozor.repo.UsersRepository;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UsersRepository usersRepository;


    public Message createMessage(MessageDTO dto) {
        Users from = usersRepository.findById(dto.getFromUserId())
                .orElseThrow(() -> new RuntimeException("Sender user not found"));

        Users to = usersRepository.findById(dto.getToUserId())
                .orElseThrow(() -> new RuntimeException("Receiver user not found"));

        Message message = new Message();
        message.setFrom(from);
        message.setTo(to);
        message.setText(dto.getText());
        message.setDate(new Date());

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessage(Integer id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));
    }

    public Message updateMessage(Integer id, MessageDTO dto) {
        Message message = getMessage(id);
        message.setText(dto.getText());

        if (dto.getFromUserId() != null) {
            Users from = usersRepository.findById(dto.getFromUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            message.setFrom(from);
        }

        if (dto.getToUserId() != null) {
            Users to = usersRepository.findById(dto.getToUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            message.setTo(to);
        }

        return messageRepository.save(message);
    }

    public void deleteMessage(Integer id) {
        messageRepository.deleteById(id);
    }

    public List<Message> getMessagesBetween(Integer from, Integer to) {
        return messageRepository.findByFromIdAndToIdOrFromIdAndToId(from, to, to, from);
    }

    public List<User2DTO> getUsersWithMessages(Integer currentUserId) {
        List<Message> messages = messageRepository.findByFromIdOrToId(currentUserId, currentUserId);
        Set<Integer> userIds = new HashSet<>();

        for (Message message : messages) {
            if (message.getFrom().getId().equals(currentUserId)) {
                userIds.add(message.getTo().getId());
            } else {
                userIds.add(message.getFrom().getId());
            }
        }

        List<Users> users = usersRepository.findAllById(userIds);
        return users.stream()
                .map(user -> new User2DTO(user.getId(), user.getNickname(), user.getPhoto().getId()))
                .collect(Collectors.toList());
    }

}

