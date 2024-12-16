package base.backend.Base.Project.services;

import base.backend.Base.Project.models.Store;
import base.backend.Base.Project.models.dto.StoreDTO;
import base.backend.Base.Project.repositories.StoreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public Optional<Store> getStoreById(Integer id) {
        return storeRepository.findById(id);
    }

    public Store createStore(StoreDTO storeDTO) {
        Store store = new Store(storeDTO);
        return storeRepository.save(store);
    }

    public void deleteStore(Integer id) {
        if (!storeRepository.existsById(id)) {
            throw storeNotFound();
        }
        storeRepository.deleteById(id);
    }

    private ResponseStatusException storeNotFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found");
    }
}
