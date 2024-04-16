package movie.cinemate.cinemate.repository.jdbctemplate;

import lombok.extern.slf4j.Slf4j;
import movie.cinemate.cinemate.entity.movie.*;
import movie.cinemate.cinemate.dto.movie.MovieDetailDto;
import movie.cinemate.cinemate.dto.movie.MovieDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Slf4j
@Repository
public class MovieDaoImpl {

    private final JdbcTemplate template;

    public MovieDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    public List<MovieDto> findAll(int page) {
        String sql = "select * from (select * from movie order by movie.popularity desc limit ? offset ?) m " +
                "left join movie_genre mg on m.movie_id = mg.movie_id " +
                "left join genre g on g.genre_id = mg.genre_id";

        List<Object> param = new ArrayList<>();
        param.add(30);
        param.add((page-1) * 30);

        log.info("param={}", param);
        Map<Long, MovieDto> map = new LinkedHashMap<>();
        template.query(sql, getRowMapper(map, MovieDto.class), param.toArray());
//        template.queryForObject(sql, getRowMapper(map, MovieDto.class), param.toArray());
        return map.values().stream().toList();
    }

    public MovieDetailDto getMovieDetail(Long movieId) {
        MovieDetailDto movie = getMovie(movieId, MovieDetailDto.class);
        log.info("movie={}", movie);
        movie.setActors(getCasts(movieId));
        movie.setDirectors(getCrews(movieId));
        movie.setVideos(getVideos(movieId));

        return movie;
    }

    private <T extends MovieDto> RowMapper<Object> getRowMapper(Map<Long, T> map, Class<T> clazz) {
        return (rs, rowNum) -> {
            Long movieId = rs.getLong("movie_id");
            if (map.get(movieId) == null) {
                map.put(movieId, new BeanPropertyRowMapper<>(clazz).mapRow(rs, rowNum));
            }
            map.get(movieId).getGenres().add((new BeanPropertyRowMapper<>(Genre.class)).mapRow(rs, rowNum));
            return null;
        };
    }

    public <T extends MovieDto> T getMovie(Long movieId, Class<T> clazz) {
        String query = "SELECT m.movie_id, m.title, m.original_title, m.overview, m.poster_path, m.backdrop_path, m.runtime, " +
                "m.release_date, m.vote_average, m.popularity, g.genre_id, g.name FROM movie m " +
                "left JOIN movie_genre mg ON m.movie_id = mg.movie_id " +
                "left JOIN genre g ON mg.genre_id = g.genre_id " +
                "WHERE m.movie_id = ?;";
        Map<Long, T> map = new HashMap<>();
        template.queryForObject(query, (rs, rowNum) -> {
            if (map.get(movieId) == null) {
                map.put(movieId, (new BeanPropertyRowMapper<>(clazz)).mapRow(rs, rowNum));
            }

            if (rs.getLong("genre_id") != 0) {
                map.get(movieId).getGenres().add((new BeanPropertyRowMapper<>(Genre.class)).mapRow(rs, rowNum));
            }
            return null;
        }, movieId);

        return map.get(movieId);
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

//    private RowMapper<MovieDto> movieRowMapper() {
//        return BeanPropertyRowMapper.newInstance(MovieDto.class);
//    }
//
//    private RowMapper<MovieDto> movieDtoRowMapper() {
//        return ((rs, rowNum) -> {
//            MovieDto movieDto = new MovieDto();
//            movieDto.setMovieId(rs.getLong("movie_id"));
//            movieDto.setTitle(rs.getString("title"));
//            movieDto.setOriginalTitle(rs.getString("original_title"));
//            movieDto.setOverview(rs.getString("overview"));
//            movieDto.setPosterPath(rs.getString("poster_path"));
//            movieDto.setBackdropPath(rs.getString("backdrop_path"));
//            movieDto.setRuntime(rs.getInt("runtime"));
//            movieDto.setReleaseDate(rs.getTimestamp("release_date").toLocalDateTime());
//            movieDto.setVoteAverage(rs.getFloat("vote_average"));
//            movieDto.setPopularity(rs.getFloat("popularity"));
//            return movieDto;
//        });
//    }

    //    public MovieDto getMovie(Long movieId) {
//        String query = "SELECT m.movie_id, m.title, m.original_title, m.overview, m.poster_path, m.backdrop_path, m.runtime, " +
//                "m.release_date, m.vote_average, m.popularity, g.genre_id, g.name FROM movie m " +
//                "JOIN movie_genre mg ON m.movie_id = mg.movie_id " +
//                "JOIN genre g ON mg.genre_id = g.genre_id " +
//                "WHERE m.movie_id = ?;";
//        Map<Long, MovieDto> map = new HashMap<>();
//
//        MovieDto movie = new MovieDto();
//        List<Genre> genres = new ArrayList<>();
//        template.query(query, getRowMapper(map, MovieDto.class), movieId);
//        return map.get(movieId);
//    }

//    private static RowMapper<Object> getRowMapper(Long movieId, Map<Long, MovieDto> map) {
//        return (rs, rowNum) -> {
//            if (map.get(movieId) == null) {
//                map.put(movieId, (new BeanPropertyRowMapper<>(MovieDto.class)).mapRow(rs, rowNum));
//            }
//            map.get(movieId)
//               .getGenres()
//               .add((new BeanPropertyRowMapper<>(Genre.class)).mapRow(rs, rowNum));
//            return null;
//        };
//    }
}