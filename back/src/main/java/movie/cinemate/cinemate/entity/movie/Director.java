package movie.cinemate.cinemate.entity.movie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Director {
    private Long directorId;
    private String name;
    private String profilePath;
}
