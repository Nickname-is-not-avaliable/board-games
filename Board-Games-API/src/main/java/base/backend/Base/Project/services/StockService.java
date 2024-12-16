package base.backend.Base.Project.services;

import base.backend.Base.Project.models.Stock;
import base.backend.Base.Project.models.dto.StockDTO;
import base.backend.Base.Project.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StockService {

    private final StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Optional<Stock> getStockById(Integer stockId) {
        return stockRepository.findById(stockId);
    }

    public List<Stock> getStocksByBoardGameId(Integer boardGameId) {
        return stockRepository.findByBoardGameId(boardGameId);
    }

    public List<Stock> getStocksByStoreId(Integer storeId) {
        return stockRepository.findByStoreId(storeId);
    }

    public Stock createStock(StockDTO stockDTO) {
        Stock stock = new Stock(stockDTO);
        return stockRepository.save(stock);
    }

    public Stock updateStock(Integer stockId, Map<String, Object> updates) {
        Stock existingStock = stockRepository.findById(stockId)
                .orElseThrow(this::stockNotFound);

        if (updates.containsKey("quantity")) {
            existingStock.setQuantity((Integer) updates.get("quantity"));
        }

        return stockRepository.save(existingStock);
    }

    public void deleteStock(Integer stockId) {
        if (!stockRepository.existsById(stockId)) {
            throw stockNotFound();
        }
        stockRepository.deleteById(stockId);
    }

    private ResponseStatusException stockNotFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock not found");
    }
}
