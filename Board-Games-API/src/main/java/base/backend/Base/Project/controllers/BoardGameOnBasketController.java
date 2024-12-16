package base.backend.Base.Project.controllers;

import base.backend.Base.Project.models.BoardGameOnBasket;
import base.backend.Base.Project.models.dto.BoardGameOnBasketDTO;
import base.backend.Base.Project.services.BoardGameOnBasketService;
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
@RequestMapping("api/board-games-on-basket")
@Tag(name = "On Basket")
public class BoardGameOnBasketController {

    private final BoardGameOnBasketService boardGameOnBasketService;

    @Autowired
    public BoardGameOnBasketController(BoardGameOnBasketService boardGameOnBasketService) {
        this.boardGameOnBasketService = boardGameOnBasketService;
    }

    @GetMapping
    public ResponseEntity<List<BoardGameOnBasketDTO>> getAllBoardGamesOnBasket() {
        List<BoardGameOnBasket> boardGamesOnBasket = boardGameOnBasketService.getAllBoardGamesOnBasket();
        List<BoardGameOnBasketDTO> boardGameOnBasketDTOs = boardGamesOnBasket.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(boardGameOnBasketDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardGameOnBasketDTO> getBoardGameOnBasketById(@PathVariable Integer id) {
        Optional<BoardGameOnBasket> boardGameOnBasket = boardGameOnBasketService.getBoardGameOnBasketById(id);
        return boardGameOnBasket
                .map(basket -> ResponseEntity.ok(convertToDTO(basket)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<BoardGameOnBasketDTO>> getBoardGamesOnBasketByUserId(@PathVariable Integer userId) {
        List<BoardGameOnBasket> boardGamesOnBasket = boardGameOnBasketService.getBoardGamesOnBasketByUserId(userId);
        List<BoardGameOnBasketDTO> boardGameOnBasketDTOs = boardGamesOnBasket.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(boardGameOnBasketDTOs);
    }

    @PostMapping
    public ResponseEntity<BoardGameOnBasketDTO> createBoardGameOnBasket(@RequestBody BoardGameOnBasketDTO boardGameOnBasketDTO) {
        BoardGameOnBasket newBoardGameOnBasket = boardGameOnBasketService.createBoardGameOnBasket(boardGameOnBasketDTO);
        BoardGameOnBasketDTO newBoardGameOnBasketDTO = convertToDTO(newBoardGameOnBasket);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBoardGameOnBasketDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BoardGameOnBasketDTO> updateBoardGameOnBasket(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> updates
    ) {
        BoardGameOnBasket updatedBoardGameOnBasket = boardGameOnBasketService.updateBoardGameOnBasket(id, updates);
        BoardGameOnBasketDTO updatedBoardGameOnBasketDTO = convertToDTO(updatedBoardGameOnBasket);
        return ResponseEntity.ok(updatedBoardGameOnBasketDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoardGameOnBasket(@PathVariable Integer id) {
        boardGameOnBasketService.deleteBoardGameOnBasket(id);
        return ResponseEntity.noContent().build();
    }

    private BoardGameOnBasketDTO convertToDTO(BoardGameOnBasket boardGameOnBasket) {
        return new BoardGameOnBasketDTO(boardGameOnBasket);
    }
}
