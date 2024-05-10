package movie.cinemate.cinemate.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@AllArgsConstructor
public class ReviewResource extends RepresentationModel {
    Long reviewId;
}
