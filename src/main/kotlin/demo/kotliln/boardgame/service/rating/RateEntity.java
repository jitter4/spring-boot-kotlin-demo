package demo.kotliln.boardgame.service.rating;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Entity
@Getter
public class RateEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "boardGameName", nullable = false)
    private String boardGameName;


    @Column(name = "rate", nullable = false)
    private Double rate;

}