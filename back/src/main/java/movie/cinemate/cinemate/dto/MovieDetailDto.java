package movie.cinemate.cinemate.dto;

import lombok.Getter;
import lombok.Setter;
import movie.cinemate.cinemate.entity.movie.Actor;
import movie.cinemate.cinemate.entity.movie.Director;
import movie.cinemate.cinemate.entity.movie.Genre;
import movie.cinemate.cinemate.entity.movie.Video;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MovieDetailDto {
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
}
