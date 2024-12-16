package base.backend.Base.Project.models;

import base.backend.Base.Project.models.dto.BoardGameOnBasketDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "board_game_on_basket")
public class BoardGameOnBasket {
    public BoardGameOnBasket(BoardGameOnBasketDTO boardGameOnBasketDTO) {
        this.boardGameOnBasketId = boardGameOnBasketDTO.getBoardGameOnBasketId();
        this.userId = boardGameOnBasketDTO.getUserId();
        this.boardGameId = boardGameOnBasketDTO.getBoardGameId();
        this.quantity = boardGameOnBasketDTO.getQuantity();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_game_on_basket_id")
    private Integer boardGameOnBasketId;
    @Column(name = "user_id")
    private Integer userId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;
    @Column(name = "board_game_id")
    private Integer boardGameId;
    private Integer quantity;
}