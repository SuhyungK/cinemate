package movie.cinemate.cinemate.repository;

import lombok.extern.slf4j.Slf4j;
import movie.cinemate.cinemate.domain.Movie;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

import static movie.cinemate.cinemate.repository.DBConnectionUtil.getConnection;

@Slf4j
@Repository
public class MovieRepository {
    public List<Movie> all() {

        return null;
    }

    public Movie save(Movie movie) throws SQLException {
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
//                System.out.println("=====" + movie.getId() + " " + genreId);
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

    private void close(Connection con, Statement stmt, ResultSet rs) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }

        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
    }

}

class DBConnectionUtil {
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/cinemate", "sa", "alsk1029!");
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}