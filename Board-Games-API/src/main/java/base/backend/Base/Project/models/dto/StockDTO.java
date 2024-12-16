package base.backend.Base.Project.models.dto;

import base.backend.Base.Project.models.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {
    public StockDTO(Stock stock) {
        this.stockId = stock.getStockId();
        this.storeId = stock.getStoreId();
        this.boardGameId = stock.getBoardGameId();
        this.quantity = stock.getQuantity();
    }

    private Integer stockId;
    private Integer boardGameId;
    private Integer storeId;
    private Integer quantity;
}
