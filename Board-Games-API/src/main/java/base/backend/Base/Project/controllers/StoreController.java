package base.backend.Base.Project.controllers;

import base.backend.Base.Project.models.Store;
import base.backend.Base.Project.models.dto.StoreDTO;
import base.backend.Base.Project.services.StoreService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/stores")
@Tag(name = "Stores")
public class StoreController {

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    public ResponseEntity<List<StoreDTO>> getAllStores() {
        List<Store> stores = storeService.getAllStores();
        List<StoreDTO> storeDTOs = stores.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(storeDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDTO> getStoreById(@PathVariable Integer id) {
        Optional<Store> store = storeService.getStoreById(id);
        return store
                .map(s -> ResponseEntity.ok(convertToDTO(s)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StoreDTO> createStore(@RequestBody StoreDTO storeDTO) {
        Store newStore = storeService.createStore(storeDTO);
        StoreDTO newStoreDTO = convertToDTO(newStore);
        return ResponseEntity.status(HttpStatus.CREATED).body(newStoreDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Integer id) {
        storeService.deleteStore(id);
        return ResponseEntity.noContent().build();
    }

    private StoreDTO convertToDTO(Store store) {
        return new StoreDTO(store);
    }
}
