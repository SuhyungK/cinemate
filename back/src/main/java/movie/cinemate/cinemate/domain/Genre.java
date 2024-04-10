package movie.cinemate.cinemate.domain;

import lombok.Data;

@Data
public class Genre {
    private Long id;
    private String name;

    public Genre() {};

    public Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
