package movie.cinemate.cinemate.repository.jdbc;

import lombok.extern.slf4j.Slf4j;
import movie.cinemate.cinemate.dto.movie.MovieDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MovieJdbcRepositoryTest {

    MovieJdbcRepository movieRepository = new MovieJdbcRepository();

    @Test
    void 검색_시작_단어() {
        String keyword = "해리 포터";
        List<MovieDto> movieResult = movieRepository.findByKeyword(keyword);
        for (MovieDto movie : movieResult) {
            log.info("title = {}", movie.getTitle());
            Assertions.assertTrue(movie.getTitle().startsWith(keyword));
        }
    }

    @Test
    void 검색_중간_단어() {
        String keyword = "사랑";
        List<MovieDto> movieResult = movieRepository.findByKeyword(keyword);
        for (MovieDto movie : movieResult) {
            log.info("title = {}", movie.getTitle());
            assertTrue(movie.getTitle().contains(keyword));
        }
    }
}