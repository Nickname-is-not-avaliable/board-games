package base.backend.Base.Project.controllers;

import base.backend.Base.Project.models.Stock;
import base.backend.Base.Project.models.dto.StockDTO;
import base.backend.Base.Project.services.StockService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/stocks")
@Tag(name = "Stocks")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<List<StockDTO>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStocks();
        List<StockDTO> stockDTOs = stocks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(stockDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockDTO> getStockById(@PathVariable Integer id) {
        Optional<Stock> stock = stockService.getStockById(id);
        return stock
                .map(s -> ResponseEntity.ok(convertToDTO(s)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-board-game/{boardGameId}")
    public ResponseEntity<List<StockDTO>> getStocksByBoardGameId(@PathVariable Integer boardGameId) {
        List<Stock> stocks = stockService.getStocksByBoardGameId(boardGameId);
        List<StockDTO> stockDTOs = stocks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(stockDTOs);
    }

    @GetMapping("/by-store/{storeId}")
    public ResponseEntity<List<StockDTO>> getStocksByStoreId(@PathVariable Integer storeId) {
        List<Stock> stocks = stockService.getStocksByStoreId(storeId);
        List<StockDTO> stockDTOs = stocks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(stockDTOs);
    }

    @PostMapping
    public ResponseEntity<StockDTO> createStock(@RequestBody StockDTO stockDTO) {
        Stock newStock = stockService.createStock(stockDTO);
        StockDTO newStockDTO = convertToDTO(newStock);
        return ResponseEntity.status(HttpStatus.CREATED).body(newStockDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StockDTO> updateStock(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> updates
    ) {
        Stock updatedStock = stockService.updateStock(id, updates);
        StockDTO updatedStockDTO = convertToDTO(updatedStock);
        return ResponseEntity.ok(updatedStockDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Integer id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

    private StockDTO convertToDTO(Stock stock) {
        return new StockDTO(stock);
    }
}
