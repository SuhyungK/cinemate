package movie.cinemate.cinemate.dto.movie;

import lombok.Getter;
import lombok.Setter;
import movie.cinemate.cinemate.entity.movie.Actor;
import movie.cinemate.cinemate.entity.movie.Director;
import movie.cinemate.cinemate.entity.movie.Video;

import java.util.List;

@Getter
@Setter
public class MovieDetailDto extends MovieDto {
    private List<Actor> actors;
    private List<Director> directors;
    private List<Video> videos;
}
