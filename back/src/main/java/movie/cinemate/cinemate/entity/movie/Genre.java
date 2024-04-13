package movie.cinemate.cinemate.entity.movie;

import lombok.*;
import org.simpleflatmapper.map.annotation.Column;
import org.simpleflatmapper.map.annotation.Key;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    @Column("genre_id")
    private Long genreId;
    private String name;
}
