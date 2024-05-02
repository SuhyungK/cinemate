package movie.cinemate.cinemate.service.impl;

import lombok.RequiredArgsConstructor;
import movie.cinemate.cinemate.dto.movie.MovieDto;
import movie.cinemate.cinemate.dto.movie.MovieResponseDto;
import movie.cinemate.cinemate.repository.jdbctemplate.MovieDaoImpl;
import movie.cinemate.cinemate.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieDaoImpl movieDao;

    @Override
    public List<MovieDto> findAll(int page, int size) {
        return movieDao.findAll(page, size);
    }

    @Override
    public MovieResponseDto findById(Long movieId) {
        return movieDao.findById(movieId);
    }

    @Override
    public void searchMovie(String q) {


    }

    @Override
    public List<MovieResponseDto> findMovieDetail() {
        return List.of();
    }
}
