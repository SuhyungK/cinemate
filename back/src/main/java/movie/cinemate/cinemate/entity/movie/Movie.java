package movie.cinemate.cinemate.entity.movie;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class Movie {
    private Long id;
    private String backdropPath;
    private String title;
    private String originalTitle;
    private String overview;
    private String posterPath;
    private List<Long> genreIds;
    private Float popularity;
    private LocalDateTime releaseDate;
    private Float voteAverage;

    private Movie(Long id, String backdropPath, String title, String originalTitle, String overview, String posterPath, List<Long> genreIds, Float popularity, LocalDateTime releaseDate, Float voteAverage) {
        this.id = id;
        this.backdropPath = backdropPath;
        this.title = title;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.posterPath = posterPath;
        this.genreIds = genreIds;
        this.popularity = popularity;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
    }

    public static Movie of(Long id, String backdropPath, String title, String originalTitle, String overview, String posterPath, List<Long> genreIds, Float popularity, LocalDateTime releaseDate, Float voteAverage) {
        return new Movie(id, backdropPath, title, originalTitle, overview, posterPath, genreIds, popularity, releaseDate, voteAverage);
    }
}
