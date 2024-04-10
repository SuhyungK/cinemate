import java.util.List;

// movie_id 만 얻기
public class TMDBMovieID {
    private Long page;
    private List<MovieId> results;

    public Long getPage() {
        return page;
    }

    public List<MovieId> getResults() {
        return results;
    }

    public class MovieId {
        private String id;

        public String getId() {
            return id;
        }
    }
}