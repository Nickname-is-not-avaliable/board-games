package base.backend.Base.Project.services;

import base.backend.Base.Project.models.BoardGameOnBasket;
import base.backend.Base.Project.models.dto.BoardGameOnBasketDTO;
import base.backend.Base.Project.repositories.BoardGameOnBasketRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BoardGameOnBasketService {

    private final BoardGameOnBasketRepository boardGameOnBasketRepository;

    public BoardGameOnBasketService(BoardGameOnBasketRepository boardGameOnBasketRepository) {
        this.boardGameOnBasketRepository = boardGameOnBasketRepository;
    }

    public List<BoardGameOnBasket> getAllBoardGamesOnBasket() {
        return boardGameOnBasketRepository.findAll();
    }

    public Optional<BoardGameOnBasket> getBoardGameOnBasketById(Integer id) {
        return boardGameOnBasketRepository.findById(id);
    }

    public List<BoardGameOnBasket> getBoardGamesOnBasketByUserId(Integer basketId) {
        return boardGameOnBasketRepository.findByUserId(basketId);
    }

    public BoardGameOnBasket createBoardGameOnBasket(BoardGameOnBasketDTO boardGameOnBasketDTO) {
        BoardGameOnBasket newBoardGameOnBasket = new BoardGameOnBasket(boardGameOnBasketDTO);
        return boardGameOnBasketRepository.save(newBoardGameOnBasket);
    }

    public BoardGameOnBasket updateBoardGameOnBasket(Integer id, Map<String, Object> updates) {
        BoardGameOnBasket existingBoardGameOnBasket = boardGameOnBasketRepository
                .findById(id)
                .orElseThrow(this::boardGameOnBasketNotFound);

        if (updates.containsKey("boardGameId")) {
            existingBoardGameOnBasket.setBoardGameId((Integer) updates.get("boardGameId"));
        }

        if (updates.containsKey("quantity")) {
            existingBoardGameOnBasket.setQuantity((Integer) updates.get("quantity"));
        }

        return boardGameOnBasketRepository.save(existingBoardGameOnBasket);
    }

    public void deleteBoardGameOnBasket(Integer id) {
        boardGameOnBasketRepository.deleteById(id);
    }

    private ResponseStatusException boardGameOnBasketNotFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "BoardGameOnBasket not found");
    }
}
