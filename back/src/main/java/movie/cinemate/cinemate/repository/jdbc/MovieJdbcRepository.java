package movie.cinemate.cinemate.repository.jdbc;

import lombok.extern.slf4j.Slf4j;
import movie.cinemate.cinemate.domain.Movie;
import movie.cinemate.cinemate.domain.MovieDTO;
import movie.cinemate.cinemate.repository.MovieRepository;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static movie.cinemate.cinemate.repository.jdbc.DBConnectionUtil.*;

@Slf4j
@Repository
public class MovieJdbcRepository implements MovieRepository {

    /**
     * 전체 영화 조회
     */
    @Override
    public List<MovieDTO> findAll() {
        String sql = "select * from movie";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            List<MovieDTO> movies = new ArrayList<>();
            while (rs.next()) {
                MovieDTO movie = MovieDTO.of(
                        rs.getLong("movie_id"),
                        rs.getString("title"),
                        rs.getString("original_title"),
                        rs.getString("overview"),
                        rs.getString("poster_path"),
                        rs.getString("backdrop_path"),
                        rs.getInt("runtime"),
                        rs.getDate("release_date"),
                        rs.getFloat("vote_average")
                );
                movies.add(movie);
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
    @Override
    public List<MovieDTO> findByKeyword(String keyword) {
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
            List<MovieDTO> movies = new ArrayList<>();
            while (rs.next()) {
                MovieDTO movie = MovieDTO.of(
                        rs.getLong("movie_id"),
                        rs.getString("title"),
                        rs.getString("original_title"),
                        rs.getString("overview"),
                        rs.getString("poster_path"),
                        rs.getString("backdrop_path"),
                        rs.getInt("runtime"),
                        rs.getDate("release_date"),
                        rs.getFloat("vote_average")
                );
                movies.add(movie);
            }
            return movies;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(con, ps, rs);
        }
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