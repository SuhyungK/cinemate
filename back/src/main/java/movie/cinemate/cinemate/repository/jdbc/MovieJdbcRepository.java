package movie.cinemate.cinemate.repository.jdbc;

import lombok.extern.slf4j.Slf4j;
import movie.cinemate.cinemate.entity.movie.Movie;
import movie.cinemate.cinemate.dto.MovieDetailDto;
import movie.cinemate.cinemate.dto.MovieDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static movie.cinemate.cinemate.repository.jdbc.DBConnectionUtil.*;

@Slf4j
public class MovieJdbcRepository {

    /**
     * 전체 영화 조회
     */
    public List<MovieDto> findAll(int page) {
        String sql = "select * from movie";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            List<MovieDto> movies = new ArrayList<>();
            while (rs.next()) {
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
                movies.add(movieDto);
            }
            return movies;
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            close(con, ps, null);
        }
        return List.of();
    }

    /**
     * 키워드로 영화 찾기
     */
    public List<MovieDto> findByKeyword(String keyword) {
        String sql = "select * from movie where title like ?";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            log.info("sql statement={}", ps);
            rs = ps.executeQuery();
            List<MovieDto> movies = new ArrayList<>();
            while (rs.next()) {
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
                movies.add(movieDto);
            }
            return movies;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(con, ps, rs);
        }
    }

    public Optional<MovieDto> findDetail(Long movieId) {
        return Optional.empty();
    }

    public List<MovieDetailDto> getMovieDetailsWithGenres() {
        return List.of();
    }

    /**
     * 영화 저장 (개발용)
     */
    private Movie save(Movie movie) throws SQLException {
        String sql = "insert into movie " +
                "(movie_id, backdrop_path, title, original_title, overview, " +
                "poster_path, popularity, release_date, vote_average) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String subsql = "insert into movie_genre (movie_id, genre_id) values (?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, movie.getId());
            pstmt.setString(2, movie.getBackdropPath());
            pstmt.setString(3, movie.getTitle());
            pstmt.setString(4, movie.getOriginalTitle());
            pstmt.setString(5, movie.getOverview());
            pstmt.setString(6, movie.getPosterPath());
            pstmt.setFloat(7, movie.getPopularity());
            pstmt.setTimestamp(8, Timestamp.valueOf(movie.getReleaseDate()));
            pstmt.setFloat(9, movie.getVoteAverage());
            pstmt.executeUpdate();

            pstmt = con.prepareStatement(subsql);
            for (Long genreId : movie.getGenreIds()) {
                pstmt.setLong(1, movie.getId());
                pstmt.setLong(2, genreId);
                pstmt.executeUpdate();
            }
            return movie;
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }
    }
}