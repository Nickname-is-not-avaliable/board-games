package base.backend.Base.Project.models;

import base.backend.Base.Project.models.dto.StoreDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "stores")
public class Store {
    public Store(StoreDTO storeDTO) {
        this.storeId = storeDTO.getStoreId();
        this.address = storeDTO.getAddress();
        this.latitude = storeDTO.getLatitude();
        this.longitude = storeDTO.getLongitude();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Integer storeId;
    private String address;
    private Double latitude;
    private Double longitude;
}