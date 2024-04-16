package movie.cinemate.cinemate.service.impl;

import movie.cinemate.cinemate.dto.movie.MovieDto;
import movie.cinemate.cinemate.repository.jdbctemplate.MovieDaoImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieServiceImpl {
    private final MovieDaoImpl movieRepository;

    public MovieServiceImpl(MovieDaoImpl MovieRepositoryImpl) {
        this.movieRepository = MovieRepositoryImpl;
    }

    public void findAll() {

    }

    public Optional<MovieDto> findById(Long movieId) {
        return Optional.empty();
    }

    public void findByKeyword() {

    }
}
