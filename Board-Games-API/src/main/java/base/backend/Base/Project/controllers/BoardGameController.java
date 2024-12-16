package base.backend.Base.Project.controllers;

import base.backend.Base.Project.models.BoardGame;
import base.backend.Base.Project.models.dto.BoardGameDTO;
import base.backend.Base.Project.services.BoardGameService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/board-games")
@Tag(name = "BoardGames")
public class BoardGameController {

    private static final Logger logger = LoggerFactory.getLogger(BoardGameController.class);

    private final BoardGameService boardGameService;
    private final Map<String, CategoryData> categoryRatings = new HashMap<>();

    @Autowired
    public BoardGameController(BoardGameService boardGameService) {
        this.boardGameService = boardGameService;
    }

    @GetMapping
    public ResponseEntity<List<BoardGameDTO>> getAllBoardGames() {
        List<BoardGame> boardGames = boardGameService.getAllBoardGames();
        List<BoardGameDTO> boardGameDTOs = boardGames.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(boardGameDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardGameDTO> getBoardGameById(@PathVariable Integer id) {
        Optional<BoardGame> optionalBoardGame = boardGameService.getBoardGameById(id);

        optionalBoardGame.ifPresent(boardGame -> {
            String category = boardGame.getCategory();
            categoryRatings.computeIfAbsent(category, k -> new CategoryData());

            categoryRatings.get(category).incrementCategoryRating();
            categoryRatings.get(category).incrementGameCount(id);

            logger.info("Category '{}' accessed for game ID '{}'. New category rating: {}, game count: {}",
                    category, id, categoryRatings.get(category).getRating(), categoryRatings.get(category).getGameCount(id));
        });

        return optionalBoardGame
                .map(boardGame -> ResponseEntity.ok(convertToDTO(boardGame)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-category/{category}")
    public ResponseEntity<List<BoardGameDTO>> getBoardGamesByCategory(@PathVariable String category) {
        categoryRatings.computeIfAbsent(category, k -> new CategoryData()).incrementCategoryRating();
        logger.info("Category '{}' accessed. New rating: {}", category, categoryRatings.get(category).getRating());

        List<BoardGame> boardGames = boardGameService.getBoardGamesByCategory(category);
        List<BoardGameDTO> boardGameDTOs = boardGames.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(boardGameDTOs);
    }

    @GetMapping("/sorted-by-category")
    public ResponseEntity<List<BoardGameDTO>> getBoardGamesSortedByCategoryRating() {
        List<BoardGame> allGames = boardGameService.getAllBoardGames();
    
        allGames.forEach(game ->
                categoryRatings.computeIfAbsent(game.getCategory(), k -> new CategoryData()));
    
        List<BoardGameDTO> sortedGames = allGames.stream()
                .sorted((game1, game2) -> {
                    int categoryRating1 = categoryRatings.get(game1.getCategory()).getRating();
                    int categoryRating2 = categoryRatings.get(game2.getCategory()).getRating();
    
                    int gameRating1 = categoryRatings.get(game1.getCategory()).getGameCount(game1.getId());
                    int gameRating2 = categoryRatings.get(game2.getCategory()).getGameCount(game2.getId());
    
                    int totalRating1 = categoryRating1 + gameRating1;
                    int totalRating2 = categoryRating2 + gameRating2;
    
                    return Integer.compare(totalRating2, totalRating1);
                })
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    
        logger.info("Games sorted by combined category and game rating: {}", sortedGames);
    
        return ResponseEntity.ok(sortedGames);
    }
    

    @PostMapping
    public ResponseEntity<BoardGameDTO> createBoardGame(@RequestBody BoardGameDTO boardGameDTO) {
        BoardGame newBoardGame = boardGameService.createBoardGame(boardGameDTO);
        BoardGameDTO newBoardGameDTO = convertToDTO(newBoardGame);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBoardGameDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BoardGameDTO> updateBoardGame(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> updates
    ) {
        BoardGame updatedBoardGame = boardGameService.updateBoardGame(id, updates);
        BoardGameDTO updatedBoardGameDTO = convertToDTO(updatedBoardGame);
        return ResponseEntity.ok(updatedBoardGameDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoardGame(@PathVariable Integer id) {
        boardGameService.deleteBoardGame(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<BoardGameDTO>> getBoardGamesByTitleContaining(@RequestParam String searchString) {
        List<BoardGameDTO> movieDTOs = boardGameService.getBoardGamesByTitleContaining(searchString).stream()
                .map(BoardGameDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(movieDTOs);
    }

    private BoardGameDTO convertToDTO(BoardGame boardGame) {
        return new BoardGameDTO(boardGame);
    }

    private static class CategoryData {
        private int rating;
        private final Map<Integer, Integer> gameCounts = new HashMap<>();

        public void incrementCategoryRating() {
            rating++;
        }

        public void incrementGameCount(Integer gameId) {
            gameCounts.merge(gameId, 1, Integer::sum);
        }

        public int getGameCount(Integer gameId) {
            return gameCounts.getOrDefault(gameId, 0);
        }

        public Integer getRating() {
            return rating;
        }
    }
}
