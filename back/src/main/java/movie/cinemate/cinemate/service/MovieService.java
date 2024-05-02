package movie.cinemate.cinemate.service;

import movie.cinemate.cinemate.dto.movie.MovieResponseDto;
import movie.cinemate.cinemate.dto.movie.MovieDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MovieService {
    /**
     * 전체 영화 조회
     */
    public List<MovieDto> findAll(int page, int size);

    /**
     * 영화ID로 개별 영화 조회
     */
    public MovieResponseDto findById(Long movieId);

    /**
     * 키워드로 영화 찾기
     */
    public void searchMovie(String q);

    public List<MovieResponseDto> findMovieDetail();
}
