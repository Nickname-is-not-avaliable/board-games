package base.backend.Base.Project.services;

import base.backend.Base.Project.models.Comment;
import base.backend.Base.Project.models.dto.CommentDTO;
import base.backend.Base.Project.repositories.CommentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Optional<Comment> getCommentById(Integer id) {
        return commentRepository.findById(id);
    }

    public List<Comment> getCommentsByBoardGameId(Integer boardGameId) {
        return commentRepository.findByBoardGameId(boardGameId);
    }

    public void createComment(CommentDTO commentDTO) {
        Comment comment = new Comment(commentDTO);
        comment.setDate(LocalDateTime.now());
        commentRepository.save(comment);
    }

    public Comment updateComment(Integer id, Map<String, Object> updates) {
        Comment existingComment = commentRepository
                .findById(id)
                .orElseThrow(this::commentNotFound);

        if (updates.containsKey("boardGameId")) {
            existingComment.setBoardGameId((Integer) updates.get("boardGameId"));
        }

        if (updates.containsKey("userId")) {
            existingComment.setUserId((Integer) updates.get("userId"));
        }

        if (updates.containsKey("text")) {
            existingComment.setText((String) updates.get("text"));
        }

        return commentRepository.save(existingComment);
    }

    public void deleteComment(Integer id) {
        commentRepository.deleteById(id);
    }

    private ResponseStatusException commentNotFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
    }
}
