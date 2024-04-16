package movie.cinemate.cinemate.service;

import movie.cinemate.cinemate.dto.movie.MovieDetailDto;
import movie.cinemate.cinemate.dto.movie.MovieDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MovieService {
    /**
     * 전체 영화 조회
     */
    public void findAll();

    /**
     * 영화ID로 개별 영화 조회
     */
    public Optional<MovieDto> findById(Long movieId);

    /**
     * 키워드로 영화 찾기
     */
    public void findByKeyword();

    public List<MovieDetailDto> findMovieDetail();
}
