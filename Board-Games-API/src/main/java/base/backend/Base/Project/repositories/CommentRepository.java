package base.backend.Base.Project.repositories;

import base.backend.Base.Project.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByBoardGameId(Integer boardGameId);
}
