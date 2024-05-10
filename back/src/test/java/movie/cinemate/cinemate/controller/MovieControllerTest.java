package movie.cinemate.cinemate.controller;

import movie.cinemate.cinemate.dto.movie.MovieResponseDto;
import movie.cinemate.cinemate.repository.jdbctemplate.MovieDaoImpl;
import movie.cinemate.cinemate.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MovieController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class MovieControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MovieService movieService;

    Long movieId = 1L;
    MovieResponseDto movieResponseDto;

    @BeforeEach
    void init() {
        movieResponseDto = MovieResponseDto.builder()
                                           .title("title")
                                           .originalTitle("originalTitle")
                                           .overview("overview")
                                           .posterPath("posterPath")
                                           .backdropPath("backdropPath")
                                           .runtime(0)
                                           .releaseDate(LocalDateTime.of(2024, 4, 18, 13, 11))
                                           .voteAverage(0.0F)
                                           .popularity(0.0F)
                                           .genres(new ArrayList<>())
                                           .actors(new ArrayList<>())
                                           .directors(new ArrayList<>())
                                           .videos(new ArrayList<>())
                                           .build()
        ;
    }

    @Test
    void MovieController_getDetail_ReturnOneMovie() throws Exception {
        when(movieService.findById(anyLong())).thenReturn(movieResponseDto);

        mockMvc.perform(get("/api/v1/movie/{movieId}", movieId)
                       .accept(MediaType.APPLICATION_JSON)
               )
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
        ;
    }

}