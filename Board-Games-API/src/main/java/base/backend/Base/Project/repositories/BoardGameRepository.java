package base.backend.Base.Project.repositories;

import base.backend.Base.Project.models.BoardGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface BoardGameRepository extends JpaRepository<BoardGame, Integer> {
    List<BoardGame> findByCategory(String category);

    List<BoardGame> findByTitleContainingIgnoreCase(String searchString);
}
