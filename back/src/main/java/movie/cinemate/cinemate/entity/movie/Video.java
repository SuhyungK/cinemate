package movie.cinemate.cinemate.entity.movie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    private String videoId;
    private String path;
    private String name;
    private Integer size;
}
