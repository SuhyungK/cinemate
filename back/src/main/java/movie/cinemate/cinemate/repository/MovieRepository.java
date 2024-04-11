package movie.cinemate.cinemate.repository;


import movie.cinemate.cinemate.domain.MovieDTO;

import java.util.List;

public interface MovieRepository {
    public List<MovieDTO> findAll();
    public List<MovieDTO> findByKeyword(String keyword);
}
