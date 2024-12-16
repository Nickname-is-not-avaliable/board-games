package base.backend.Base.Project.repositories;

import base.backend.Base.Project.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface StockRepository extends JpaRepository<Stock, Integer> {
    List<Stock> findByBoardGameId(Integer boardGameId);

    List<Stock> findByStoreId(Integer storeId);
}
