package movie.cinemate.cinemate.service;

import movie.cinemate.cinemate.dto.movie.MovieDto;
import movie.cinemate.cinemate.repository.jdbctemplate.MovieRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieServiceImpl {
    private final MovieRepositoryImpl movieRepository;

    public MovieServiceImpl(MovieRepositoryImpl MovieRepositoryImpl) {
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
