package demo.kotliln.boardgame.service.entities;

import demo.kotliln.boardgame.api.models.Category;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class BoardGameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "min_number_of_players", nullable = false)
    private Integer minimalNumberOfPlayers;

    @Column(name = "max_number_of_players", nullable = false)
    private Integer maximalNumberOfPlayers;

    @Column(name = "minimal_age", nullable = false)
    private Integer minimalAge;

    @Column(name = "maximal_age", nullable = false)
    private Integer maximalAge;

}