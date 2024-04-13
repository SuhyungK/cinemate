package movie.cinemate.cinemate.repository.jdbctemplate;

import lombok.extern.slf4j.Slf4j;
import movie.cinemate.cinemate.entity.movie.*;
import movie.cinemate.cinemate.dto.MovieDetailDto;
import movie.cinemate.cinemate.dto.MovieDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Slf4j
@Repository
public class MovieRepositoryImpl {

    private final JdbcTemplate template;

    public MovieRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    public List<MovieDto> findAll(int page) {
        String sql = "select * from movie limit ? offset ?";
        return template.query(sql, movieDtoRowMapper(), 30, (page-1) * 30);
    }

    public MovieDto getMovie(Long movieId) {
        String query = "SELECT m.movie_id, m.title, m.original_title, m.overview, m.poster_path, m.backdrop_path, m.runtime, " +
                "m.release_date, m.vote_average, m.popularity, g.genre_id, g.name FROM movie m " +
                "JOIN movie_genre mg ON m.movie_id = mg.movie_id " +
                "JOIN genre g ON mg.genre_id = g.genre_id " +
                "WHERE m.movie_id = ?;";
        Map<Long, MovieDto> map = new HashMap<>();

        template.query(query, (rs, rowNum) -> {
            if (map.get(movieId) == null) {
                map.put(movieId, (new BeanPropertyRowMapper<>(MovieDto.class)).mapRow(rs, rowNum));
            }
            map.get(movieId).getGenres().add((new BeanPropertyRowMapper<>(Genre.class)).mapRow(rs, rowNum));
            return null;
        }, movieId);

        return map.get(movieId);
    }

    public <T extends MovieDto> T getMovie(Long movieId, Class<T> clazz) {
        String query = "SELECT m.movie_id, m.title, m.original_title, m.overview, m.poster_path, m.backdrop_path, m.runtime, " +
                "m.release_date, m.vote_average, m.popularity, g.genre_id, g.name FROM movie m " +
                "JOIN movie_genre mg ON m.movie_id = mg.movie_id " +
                "JOIN genre g ON mg.genre_id = g.genre_id " +
                "WHERE m.movie_id = ?;";
        Map<Long, T> map = new HashMap<>();

        template.query(query, (rs, rowNum) -> {
            if (map.get(movieId) == null) {
                map.put(movieId, (new BeanPropertyRowMapper<>(clazz)).mapRow(rs, rowNum));
            }
            map.get(movieId).getGenres().add((new BeanPropertyRowMapper<>(Genre.class)).mapRow(rs, rowNum));
            return null;
        }, movieId);

        return map.get(movieId);
    }

    public MovieDetailDto getMovieDetail(Long movieId) {
//        String query = "SELECT m.movie_id, m.title, m.original_title, m.overview, m.poster_path, m.backdrop_path, m.runtime, " +
//                "m.release_date, m.vote_average, m.popularity, g.genre_id, g.name FROM movie m " +
//                "JOIN movie_genre mg ON m.movie_id = mg.movie_id " +
//                "JOIN genre g ON mg.genre_id = g.genre_id " +
//                "WHERE m.movie_id = ?;";
//        Map<Long, MovieDetailDto> map = new HashMap<>();
//
//        template.query(query, (rs, rowNum) -> {
//            if (map.get(movieId) == null) {
//                map.put(movieId, (new BeanPropertyRowMapper<>(MovieDetailDto.class)).mapRow(rs, rowNum));
//            }
//            map.get(movieId).getGenres().add((new BeanPropertyRowMapper<>(Genre.class)).mapRow(rs, rowNum));
//            return null;
//        }, movieId);

        MovieDetailDto movie = getMovie(movieId, MovieDetailDto.class);
        movie.setActors(getCasts(movieId));
        movie.setDirectors(getCrews(movieId));
        movie.setVideos(getVideos(movieId));

        return movie;
    }

    private List<Actor> getCasts(Long movieId) {
        return template.query("select a.* from actor a " +
                        "join cast c on a.actor_id = c.actor_id " +
                        "join movie m on m.movie_id = c.movie_id " +
                        "where m.movie_id = ?",
                BeanPropertyRowMapper.newInstance(Actor.class), movieId);
    }

    private List<Director> getCrews(Long movieId) {
        return template.query("select d.* from director d " +
                        "join crew c on d.director_id = c.director_id " +
                        "join movie m on m.movie_id = c.movie_id " +
                        "where m.movie_id = ?",
                BeanPropertyRowMapper.newInstance(Director.class), movieId);
    }
    private List<Video> getVideos(Long movieId) {
        return template.query("select v.* from video v " +
                "join movie m on v.movie_id = m.movie_id where m.movie_id = ?",
                BeanPropertyRowMapper.newInstance(Video.class), movieId);
    }

    private RowMapper<MovieDto> movieRowMapper() {
        return BeanPropertyRowMapper.newInstance(MovieDto.class);
    }

    private RowMapper<MovieDto> movieDtoRowMapper() {
        return ((rs, rowNum) -> {
            MovieDto movieDto = new MovieDto();
            movieDto.setMovieId(rs.getLong("movie_id"));
            movieDto.setTitle(rs.getString("title"));
            movieDto.setOriginalTitle(rs.getString("original_title"));
            movieDto.setOverview(rs.getString("overview"));
            movieDto.setPosterPath(rs.getString("poster_path"));
            movieDto.setBackdropPath(rs.getString("backdrop_path"));
            movieDto.setRuntime(rs.getInt("runtime"));
            movieDto.setReleaseDate(rs.getTimestamp("release_date").toLocalDateTime());
            movieDto.setVoteAverage(rs.getFloat("vote_average"));
            movieDto.setPopularity(rs.getFloat("popularity"));
            return movieDto;
        });
    }
}