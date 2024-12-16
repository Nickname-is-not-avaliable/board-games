package base.backend.Base.Project.services;

import base.backend.Base.Project.models.BoardGame;
import base.backend.Base.Project.models.dto.BoardGameDTO;
import base.backend.Base.Project.repositories.BoardGameRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BoardGameService {

    private final BoardGameRepository boardGameRepository;

    public BoardGameService(BoardGameRepository boardGameRepository) {
        this.boardGameRepository = boardGameRepository;
    }

    public List<BoardGame> getAllBoardGames() {
        return boardGameRepository.findAll();
    }

    public Optional<BoardGame> getBoardGameById(Integer id) {
        return boardGameRepository.findById(id);
    }

    public List<BoardGame> getBoardGamesByCategory(String category) {
        return boardGameRepository.findByCategory(category);
    }

    public List<BoardGame> getBoardGamesByTitleContaining(String searchString) {
        return boardGameRepository.findByTitleContainingIgnoreCase(searchString);
    }

    public BoardGame createBoardGame(BoardGameDTO boardGameDTO) {
        BoardGame newBoardGame = new BoardGame(boardGameDTO);
        return boardGameRepository.save(newBoardGame);
    }

    public BoardGame updateBoardGame(Integer id, Map<String, Object> updates) {
        BoardGame existingBoardGame = boardGameRepository
                .findById(id)
                .orElseThrow(this::boardGameNotFound);

        if (updates.containsKey("title")) {
            existingBoardGame.setTitle((String) updates.get("title"));
        }

        if (updates.containsKey("description")) {
            existingBoardGame.setDescription((String) updates.get("description"));
        }

        if (updates.containsKey("publisher")) {
            existingBoardGame.setPublisher((String) updates.get("publisher"));
        }

        if (updates.containsKey("releaseDate")) {
            existingBoardGame.setReleaseDate((String) updates.get("releaseDate"));
        }

        if (updates.containsKey("category")) {
            existingBoardGame.setCategory((String) updates.get("category"));
        }

        if (updates.containsKey("price")) {
            existingBoardGame.setPrice((BigDecimal) updates.get("price"));
        }

        if (updates.containsKey("previewImage")) {
            existingBoardGame.setPreviewImage((String) updates.get("previewImage"));
        }

        if (updates.containsKey("numberOfPlayers")) {
            existingBoardGame.setNumberOfPlayers((Integer) updates.get("numberOfPlayers"));
        }

        if (updates.containsKey("age")) {
            existingBoardGame.setAge((Integer) updates.get("age"));
        }

        if (updates.containsKey("playtime")) {
            existingBoardGame.setPlaytime((String) updates.get("playtime"));
        }

        if (updates.containsKey("reviewLink")) {
            existingBoardGame.setReviewLink((String) updates.get("reviewLink"));
        }

        if (updates.containsKey("countryOfManufacture")) {
            existingBoardGame.setCountryOfManufacture((String) updates.get("countryOfManufacture"));
        }
        return boardGameRepository.save(existingBoardGame);
    }

    public void deleteBoardGame(Integer id) {
        boardGameRepository.deleteById(id);
    }

    private ResponseStatusException boardGameNotFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "BoardGame not found");
    }
}
