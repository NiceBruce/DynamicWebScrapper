package game.statistics.collectorapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.TemporalType.TIMESTAMP;

@Entity
@Getter
@Setter
@Table(name = "games") //uniqueConstraints= @UniqueConstraint(columnNames={"name"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "GAMES_NAME", length = 100, nullable = false, unique = true)
    private String name;

    @NotNull
    @NotBlank
    private String startScore;

    private String endScore;

    private String tot_koef;

    private String b_koef;

    @NotNull
    @NotBlank
    private String timer;

    @NotNull
    @NotBlank
    private String link;

    @CreationTimestamp
    @Temporal(TIMESTAMP)
    private Date createdAt;
}
