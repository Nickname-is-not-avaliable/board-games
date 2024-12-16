package base.backend.Base.Project.controllers;

import base.backend.Base.Project.models.Comment;
import base.backend.Base.Project.models.dto.CommentDTO;
import base.backend.Base.Project.services.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/comments")
@Tag(name = "Comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        List<CommentDTO> commentDTOs = comments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(commentDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Integer id) {
        Optional<Comment> comment = commentService.getCommentById(id);
        return comment
                .map(c -> ResponseEntity.ok(convertToDTO(c)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-board-game/{boardGameId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByBoardGameId(@PathVariable Integer boardGameId) {
        List<Comment> comments = commentService.getCommentsByBoardGameId(boardGameId);
        List<CommentDTO> commentDTOs = comments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(commentDTOs);
    }

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentDTO commentDTO) {
        commentService.createComment(commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> updates
    ) {
        Comment updatedComment = commentService.updateComment(id, updates);
        CommentDTO updatedCommentDTO = convertToDTO(updatedComment);
        return ResponseEntity.ok(updatedCommentDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    private CommentDTO convertToDTO(Comment comment) {
        return new CommentDTO(comment);
    }
}
