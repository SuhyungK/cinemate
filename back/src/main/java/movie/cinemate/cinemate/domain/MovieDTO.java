package movie.cinemate.cinemate.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class MovieDTO {

    @Id
    private Long id;
    private String title;
    private String originalTitle;
    private String overview;
    private String posterPath;
    private String backdropPath;
    private Integer runtime;
    private Date releaseDate;
    private Float voteAverage;

    private MovieDTO(Long id, String title, String originalTitle, String overview, String posterPath, String backdropPath, Integer runtime, Date releaseDate, Float voteAverage) {
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.runtime = runtime;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
    }

    public static MovieDTO of(Long id, String title, String originalTitle, String overview, String posterPath, String backdropPath, Integer runtime, Date releaseDate, Float voteAverage) {
        return new MovieDTO(id, title, originalTitle, overview, posterPath, backdropPath, runtime, releaseDate, voteAverage);
    }
}
