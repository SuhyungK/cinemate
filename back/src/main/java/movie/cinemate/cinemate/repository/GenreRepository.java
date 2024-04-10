package movie.cinemate.cinemate.repository;

import lombok.extern.slf4j.Slf4j;
import movie.cinemate.cinemate.domain.Genre;
import org.springframework.stereotype.Repository;

import java.sql.*;

import static movie.cinemate.cinemate.repository.DBConnectionUtil.*;

@Slf4j
@Repository
public class GenreRepository {
    public Genre save(Genre genre) throws SQLException {
        String sql = "insert into genre (genre_id, name) values (?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, genre.getId());
            pstmt.setString(2, genre.getName());
            pstmt.executeUpdate();
            return genre;
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