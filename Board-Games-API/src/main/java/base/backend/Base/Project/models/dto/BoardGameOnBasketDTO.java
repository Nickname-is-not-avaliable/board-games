package base.backend.Base.Project.models.dto;

import base.backend.Base.Project.models.BoardGameOnBasket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardGameOnBasketDTO {
    public BoardGameOnBasketDTO(BoardGameOnBasket boardGameOnBasket) {
        this.boardGameOnBasketId = boardGameOnBasket.getBoardGameOnBasketId();
        this.userId = boardGameOnBasket.getUserId();
        this.boardGameId = boardGameOnBasket.getBoardGameId();
        this.quantity = boardGameOnBasket.getQuantity();
    }

    private Integer boardGameOnBasketId;
    private Integer userId;
    private Integer boardGameId;
    private Integer quantity;
}