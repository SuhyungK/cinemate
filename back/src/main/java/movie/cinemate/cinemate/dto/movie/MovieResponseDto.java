package movie.cinemate.cinemate.dto.movie;

import lombok.*;
import movie.cinemate.cinemate.entity.movie.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponseDto {
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

    private List<Genre> genres = new ArrayList<>();
    private List<Actor> actors;
    private List<Director> directors;
    private List<Video> videos;
    private List<Review> reviews;
}
