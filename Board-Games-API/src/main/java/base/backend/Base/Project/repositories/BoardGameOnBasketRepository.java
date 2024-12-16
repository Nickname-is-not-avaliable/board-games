package base.backend.Base.Project.repositories;

import base.backend.Base.Project.models.BoardGameOnBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface BoardGameOnBasketRepository extends JpaRepository<BoardGameOnBasket, Integer> {
    List<BoardGameOnBasket> findByUserId(Integer userId);
}
