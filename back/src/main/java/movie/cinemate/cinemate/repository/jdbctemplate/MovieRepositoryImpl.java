package movie.cinemate.cinemate.repository.jdbctemplate;

import movie.cinemate.cinemate.domain.Movie;
import movie.cinemate.cinemate.domain.MovieDTO;
import movie.cinemate.cinemate.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class MovieRepositoryImpl implements MovieRepository {

    private final JdbcTemplate jdbcTemplate;

    public MovieRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<MovieDTO> findAll() {
        return List.of();
    }

    @Override
    public List<MovieDTO> findByKeyword(String title) {
        return List.of();
    }

}
