package base.backend.Base.Project.repositories;

import base.backend.Base.Project.models.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface StoreRepository extends JpaRepository<Store, Integer> {
}
