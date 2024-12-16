package base.backend.Base.Project.models.dto;

import base.backend.Base.Project.models.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreDTO {
    public StoreDTO(Store store) {
        this.storeId = store.getStoreId();
        this.address = store.getAddress();
        this.latitude = store.getLatitude();
        this.longitude = store.getLongitude();
    }

    private Integer storeId;
    private String address;
    private Double latitude;
    private Double longitude;
}