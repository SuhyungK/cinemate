import java.util.List;

public class MovieDTO {
    private Long id;
    private String backdrop_path;
    private String title;
    private String original_title;
    private String overview;
    private String poster_path;
    private List<Long> genre_ids;
    private Float popularity;
    private String release_date;
    private Float vote_average;

    public Long getId() {
        return id;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public List<Long> getGenre_ids() {
        return genre_ids;
    }

    public Float getPopularity() {
        return popularity;
    }

    public String getRelease_date() {
        return release_date;
    }

    public Float getVote_average() {
        return vote_average;
    }

    @Override
    public String toString() {
        return "MovieDTO{" +
                "id=" + id +
                ", backdropPath='" + backdrop_path + '\'' +
                ", title='" + title + '\'' +
                ", originalTitle='" + original_title + '\'' +
                ", overview='" + overview + '\'' +
                ", posterPath='" + poster_path + '\'' +
                ", genreIds=" + genre_ids +
                ", popularity=" + popularity +
                ", releaseDate='" + release_date + '\'' +
                ", voteAverage=" + vote_average +
                '}';
    }
}
