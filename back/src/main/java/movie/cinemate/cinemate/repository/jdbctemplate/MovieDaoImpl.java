package movie.cinemate.cinemate.repository.jdbctemplate;

import lombok.extern.slf4j.Slf4j;
import movie.cinemate.cinemate.entity.movie.*;
import movie.cinemate.cinemate.dto.movie.MovieResponseDto;
import movie.cinemate.cinemate.dto.movie.MovieDto;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@Repository
public class MovieDaoImpl {

    private final JdbcTemplate template;

    public MovieDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    /**
     * 전체 영화 조회
     */
    public List<MovieDto> findAll(int page, int size) {
        String sql = "select * from (select * from movie order by movie.popularity desc limit ? offset ?) m " +
                "left join movie_genre mg on m.movie_id = mg.movie_id " +
                "left join genre g on g.genre_id = mg.genre_id";

        List<Object> param = new ArrayList<>();
        param.add(size);
        param.add((page - 1) * size);

        Map<Long, MovieDto> map = new LinkedHashMap<>();
        template.query(sql, getRowMapper(map, MovieDto.class), param.toArray());
        return map.values()
                  .stream()
                  .toList();
    }

    /**
     * 쿼리 한 번의 대략 10개 테이블 합치기
     */
    public MovieResponseDto getMovieDetailTest(Long movieId) {
        String sql = "select *, g.name as `genre_name`, a.name as `actor_name`, a.profile_path as `actor_profile_path`, v.name as `video_name`, d.name as `director_name`, d.profile_path as `director_profile_path` from movie m left join movie_genre mg on m.movie_id = mg.movie_id left join genre g on g.genre_id = mg.genre_id left join crew on m.movie_id = crew.movie_id left join director d on crew.director_id = d.director_id left join video v on v.movie_id = m.movie_id left join movie_cast mc on mc.movie_id = m.movie_id left join actor a on a.actor_id = mc.actor_id where m.movie_id = ?;";
        MovieResponseDto movie = template.query(sql, new ResultSetExtractor<MovieResponseDto>() {
            @Override
            public MovieResponseDto extractData(ResultSet rs) throws SQLException, DataAccessException {
                MovieResponseDto movie = null;
                Genre genre = null;
                Actor actor = null;
                Director director = null;
                Video video = null;

                Map<Long, Genre> genres = new LinkedHashMap<>();
                Map<Long, Actor> actors = new LinkedHashMap<>();
                Map<Long, Director> directors = new LinkedHashMap<>();
                Map<String, Video> videos = new LinkedHashMap<>();

                while (rs.next()) {
                    if (movie == null) {
                        movie = MovieResponseDto.builder()
                                                .movieId(rs.getLong("movie_id"))
                                                .title(rs.getString("title"))
                                                .originalTitle(rs.getString("original_title"))
                                                .overview(rs.getString("overview"))
                                                .posterPath(rs.getString("poster_path"))
                                                .backdropPath(rs.getString("backdrop_path"))
                                                .runtime(rs.getInt("runtime"))
                                                .releaseDate(rs.getTimestamp("release_date")
                                                            .toLocalDateTime())
                                                .voteAverage(rs.getFloat("vote_average"))
                                                .popularity(rs.getFloat("popularity"))
                                                .build();
                    }

                    if (genres.get(rs.getLong("genre_id")) == null && rs.getLong("genre_id") != 0) {
                        // first row or new genre
                        genre = new Genre();
                        genre.setGenreId(rs.getLong("genre_id"));
                        genre.setName(rs.getString("genre_name"));
                        genres.put(genre.getGenreId(), genre);
                    }

                    if (directors.get(rs.getLong("director_id")) == null && rs.getLong("director_id") != 0) {
                        director = new Director();
                        director.setDirectorId(rs.getLong("director_id"));
                        director.setName(rs.getString("director_name"));
                        director.setProfilePath(rs.getString("director_profile_path"));
                        directors.put(director.getDirectorId(), director);
                    }

                    if (videos.get(rs.getString("video_id")) == null && rs.getString("video_id") != null) {
                        video = new Video();
                        video.setVideoId(rs.getString("video_id"));
                        video.setPath(rs.getString("path"));
                        video.setName(rs.getString("video_name"));
                        video.setSize(rs.getInt("size"));
                        videos.put(video.getVideoId(), video);
                    }

                    if (actors.get(rs.getLong("actor_id")) == null && rs.getLong("actor_id") != 0) {
                        actor = new Actor();
                        actor.setActorId(rs.getLong("actor_id"));
                        actor.setName(rs.getString("actor_name"));
                        actor.setProfilePath(rs.getString("actor_profile_path"));
                        actors.put(actor.getActorId(), actor);
                    }

                }

                movie.setVideos(videos.values().stream().toList());
                movie.setActors(actors.values().stream().toList());
                movie.setGenres(genres.values().stream().toList());
                movie.setDirectors(directors.values().stream().toList());
                return movie;
            }
        }, movieId);

        return movie;
    }


    /**
     * 개별 영화 상세 조회
     * 장르, 감독, 배우, 영상
     */
    public MovieResponseDto findById(Long movieId) {
        /**
         * 쿼리 여러 번 날리기
         */
        MovieResponseDto movie = getMovie(movieId, MovieResponseDto.class);
        movie.setActors(getActors(movieId));
        movie.setDirectors(getCrews(movieId));
        movie.setVideos(getVideos(movieId));

        return movie;
    }

    /**
     * 영화 검색
     */
    public List<MovieDto> findByQ (String q) {
        String sql = "select * from movie where title like %?% and original_title like %?% order by popularity desc";

        Map<Long, MovieDto> map = new LinkedHashMap<>();
        template.queryForList(sql, getRowMapper(map, MovieDto.class),List.of(q, q));
        return map.values().stream().toList();
    }

    private <T extends MovieDto> RowMapper<Object> getRowMapper(Map<Long, T> map, Class<T> clazz) {
        return (rs, rowNum) -> {
            Long movieId = rs.getLong("movie_id");
            if (map.get(movieId) == null) {
                map.put(movieId, new BeanPropertyRowMapper<>(clazz).mapRow(rs, rowNum));
            }
            map.get(movieId)
               .getGenres()
               .add((new BeanPropertyRowMapper<>(Genre.class)).mapRow(rs, rowNum));
            return null;
        };
    }

    public <T extends MovieResponseDto> T getMovie(Long movieId, Class<T> clazz) {
        String query = "SELECT m.movie_id, m.title, m.original_title, m.overview, m.poster_path, m.backdrop_path, m.runtime, " +
                "m.release_date, m.vote_average, m.popularity, g.genre_id, g.name FROM movie m " +
                "left JOIN movie_genre mg ON m.movie_id = mg.movie_id " +
                "left JOIN genre g ON mg.genre_id = g.genre_id " +
                "WHERE m.movie_id = ?;";
        Map<Long, T> map = new HashMap<>();
        template.query(query, movieRowMapper(movieId, clazz, map), movieId);
//        template.queryForObject(query, movieRowMapper(movieId, clazz, map), movieId);
        return map.get(movieId);
    }

    private static <T extends MovieResponseDto> RowMapper<Object> movieRowMapper(Long movieId, Class<T> clazz, Map<Long, T> map) {
        return (rs, rowNum) -> {
            if (map.get(movieId) == null) {
                map.put(movieId, (new BeanPropertyRowMapper<>(clazz)).mapRow(rs, rowNum));
            }

            if (rs.getLong("genre_id") != 0) {
                map.get(movieId)
                   .getGenres()
                   .add((new BeanPropertyRowMapper<>(Genre.class)).mapRow(rs, rowNum));
            }
            return null;
        };
    }

    private List<Actor> getActors(Long movieId) {
        return template.query("select a.* from actor a " +
                        "left join movie_cast c on a.actor_id = c.actor_id " +
                        "left join movie m on m.movie_id = c.movie_id " +
                        "where m.movie_id = ?",
                BeanPropertyRowMapper.newInstance(Actor.class), movieId);
    }

    private List<Director> getCrews(Long movieId) {
        return template.query("select d.* from director d " +
                        "left join crew c on d.director_id = c.director_id " +
                        "left join movie m on m.movie_id = c.movie_id " +
                        "where m.movie_id = ?",
                BeanPropertyRowMapper.newInstance(Director.class), movieId);
    }

    private List<Video> getVideos(Long movieId) {
        return template.query("select v.* " +
                        "from video v " +
                        "left join movie m on v.movie_id = m.movie_id " +
                        "where m.movie_id = ?",
                BeanPropertyRowMapper.newInstance(Video.class), movieId);
    }

    private List<Review> getReviews(Long movieId) {
        return template.query("select * " +
                        "from movie r.* " +
                        "left join review r " +
                        "on m.movie_id = r.movie_id " +
                        "where m.movie_id = ?",
                BeanPropertyRowMapper.newInstance(Review.class), movieId);
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