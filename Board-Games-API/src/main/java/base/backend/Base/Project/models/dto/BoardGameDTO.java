package base.backend.Base.Project.models.dto;

import base.backend.Base.Project.models.BoardGame;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardGameDTO {
    public BoardGameDTO(BoardGame boardGame) {
        this.id = boardGame.getId();
        this.title = boardGame.getTitle();
        this.description = boardGame.getDescription();
        this.publisher = boardGame.getPublisher();
        this.releaseDate = boardGame.getReleaseDate();
        this.category = boardGame.getCategory();
        this.price = boardGame.getPrice();
        this.previewImage = boardGame.getPreviewImage();
        this.numberOfPlayers = boardGame.getNumberOfPlayers();
        this.age = boardGame.getAge();
        this.playtime = boardGame.getPlaytime();
        this.reviewLink = boardGame.getReviewLink();
        this.countryOfManufacture = boardGame.getCountryOfManufacture();
    }

    private Integer id;
    private String title;
    private String description;
    private String publisher;
    private String releaseDate;
    private String category;
    private BigDecimal price;
    private String previewImage;
    private Integer numberOfPlayers;
    private Integer age;
    private String playtime;
    private String reviewLink;
    private String countryOfManufacture;
}
