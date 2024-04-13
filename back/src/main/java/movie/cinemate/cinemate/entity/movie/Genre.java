package movie.cinemate.cinemate.entity.movie;

import lombok.Data;

@Data
public class Genre {
    private Long genreId;
    private String name;

    public Genre() {};

    public Genre(Long genreId, String name) {
        this.genreId = genreId;
        this.name = name;
    }
}
