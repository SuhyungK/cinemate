package movie.cinemate.cinemate.dto.movie;

import lombok.*;
import movie.cinemate.cinemate.entity.movie.Genre;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private Long movieId;
    private String title;
    private String originalTitle;
    private String overview;
    private String posterPath;
    private String backdropPath;
    private Integer runtime;
    private LocalDateTime releaseDate;
    private Float voteAverage;
    private Float popularity;

    List<Genre> genres = new ArrayList<>();
}
