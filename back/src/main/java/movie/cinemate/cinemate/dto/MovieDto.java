package movie.cinemate.cinemate.dto;

import lombok.*;

import java.util.Date;

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
    private Date releaseDate;
    private Float voteAverage;
    private Float popularity;
}
