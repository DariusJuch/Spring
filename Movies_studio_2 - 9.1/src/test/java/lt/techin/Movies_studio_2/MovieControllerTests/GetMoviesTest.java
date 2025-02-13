package lt.techin.Movies_studio_2.MovieControllerTests;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lt.techin.Movies_studio_2.controller.MovieController;
import lt.techin.Movies_studio_2.model.Actor;
import lt.techin.Movies_studio_2.model.Movie;
import lt.techin.Movies_studio_2.model.Screening;
import lt.techin.Movies_studio_2.security.SecurityConfig;
import lt.techin.Movies_studio_2.service.MovieService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MovieController.class)

@Import(SecurityConfig.class)
public class GetMoviesTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private MovieService movieService;

  // Happy path test
  @Test
  @WithMockUser(authorities = "SCOPE_ROLE_USER")
  void getMovies_whenFindAll_thenReturnAllAnd200() throws Exception {
    //given
    Movie movie1 = new Movie("Iron Man",
            "Jon Favreau",
            List.of(),
            List.of(new Actor("Robert", "Downey Jr.", 60)));

    Movie movie2 = new Movie("Captain America: The First Avenger",
            "Joe Johnston",
            List.of(new Screening("Apolo kinas", LocalDateTime.of(2025, Month.APRIL, 27, 10, 15))),
            List.of(new Actor("Christ", "Evans", 43)));

    List<Movie> movies = List.of(movie1, movie2);
    //when
    BDDMockito.given(movieService.findAllMovies()).willReturn(movies);

    // then
    mockMvc.perform(MockMvcRequestBuilders.get("/api/movies"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("length()").value(2))
            .andExpect(jsonPath("[0].id").exists())
            .andExpect(jsonPath("[0].title").value("Iron Man"))
            .andExpect(jsonPath("[0].director").value("Jon Favreau"))
            .andExpect(jsonPath("[0].screenings").exists())
            .andExpect(jsonPath("[0].screenings", Matchers.hasSize(0)))
            .andExpect(jsonPath("[0].actors").isArray())
            .andExpect(jsonPath("[0].actors", Matchers.hasSize(1)))
            .andExpect(jsonPath("[0].actors.[0].id").exists())
            .andExpect(jsonPath("[0].actors.[0].nameActor").value("Robert"))
            .andExpect(jsonPath("[0].actors.[0].lastNameActor").value("Downey Jr."))
            .andExpect(jsonPath("[0].actors.[0].age").value(60));

    //then
    mockMvc.perform(MockMvcRequestBuilders.get("/api/movies"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("length()").value(2))
            .andExpect(jsonPath("[1].id").exists())
            .andExpect(jsonPath("[1].title").value("Captain America: The First Avenger"))
            .andExpect(jsonPath("[1].director").value("Joe Johnston"))
            .andExpect(jsonPath("[1].screenings").exists())
            .andExpect(jsonPath("[1].screenings", Matchers.hasSize(1)))
            .andExpect(jsonPath("[1].screenings.[0].theatersName").value("Apolo kinas"))
            .andExpect(jsonPath("[1].screenings.[0].screeningsTime").value("2025-04-27T10:15:00"))
            .andExpect(jsonPath("[1].actors").isArray())
            .andExpect(jsonPath("[1].actors", Matchers.hasSize(1)))
            .andExpect(jsonPath("[1].actors.[0].id").exists())
            .andExpect(jsonPath("[1].actors.[0].nameActor").value("Christ"))
            .andExpect(jsonPath("[1].actors.[0].lastNameActor").value("Evans"))
            .andExpect(jsonPath("[1].actors.[0].age").value(43));

    Mockito.verify(movieService, times(2)).findAllMovies();

  }

  // Unhappy path
  @Test
  void getMovies_whenFindAllUnauthenticated_thenRespond401() throws Exception {

    // given
    Movie movie1 = new Movie("Iron Man",
            "Jon Favreau",
            List.of(),
            List.of(new Actor("Robert", "Downey Jr.", 60)));

    Movie movie2 = new Movie("Captain America: The First Avenger",
            "Joe Johnston",
            List.of(new Screening("Apolo kinas", LocalDateTime.of(2025, Month.APRIL, 27, 10, 15))),
            List.of(new Actor("Christ", "Evans", 43)));

    List<Movie> movies = List.of(movie1, movie2);
    //when
    BDDMockito.given(movieService.findAllMovies()).willReturn(movies);

    //then
    mockMvc.perform(MockMvcRequestBuilders.get("/api/movies"))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$").doesNotExist());

    Mockito.verify(movieService, times(0)).findAllMovies();

  }

  //Happy path
  @Test
  @WithMockUser(authorities = "SCOPE_ROLE_USER")
  void getMovie_whenFindMovieById_validId() throws Exception {

    // given
    Movie movie1 = new Movie("Iron Man",
            "Jon Favreau",
            List.of(),
            List.of(new Actor("Robert", "Downey Jr.", 60)));

    Movie movie2 = new Movie("Captain America: The First Avenger",
            "Joe Johnston",
            List.of(new Screening("Apolo kinas", LocalDateTime.of(2025, Month.APRIL, 27, 10, 15))),
            List.of(new Actor("Christ", "Evans", 43)));

    List<Movie> movies = List.of(movie1, movie2);
    //when
    BDDMockito.given(movieService.findById(1L)).willReturn(Optional.of(movie1));
    //then
    mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/1", 1))
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(0))
            .andExpect(jsonPath("title").value("Iron Man"));

    Mockito.verify(movieService, times(1)).findById(1);

  }

  @Test
  @WithMockUser(authorities = "SCOPE_ROLE_USER")
  void getMovie_whenFindMovieById_invalidId() throws Exception {

    // given
    Movie movie1 = new Movie("Iron Man",
            "Jon Favreau",
            List.of(),
            List.of(new Actor("Robert", "Downey Jr.", 60)));

    Movie movie2 = new Movie("Captain America: The First Avenger",
            "Joe Johnston",
            List.of(new Screening("Apolo kinas", LocalDateTime.of(2025, Month.APRIL, 27, 10, 15))),
            List.of(new Actor("Christ", "Evans", 43)));

    List<Movie> movies = List.of(movie1, movie2);
    //when
    BDDMockito.given(movieService.findById(10L)).willReturn(Optional.empty());
    //then
    mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/10", 1))
            .andExpect(status().isNotFound());


    Mockito.verify(movieService, times(1)).findById(10L);

  }

  @Test
  void getMovie_whenFindMovieById_Unauthenticated() throws Exception {

    // given
    Movie movie1 = new Movie("Iron Man",
            "Jon Favreau",
            List.of(),
            List.of(new Actor("Robert", "Downey Jr.", 60)));

    Movie movie2 = new Movie("Captain America: The First Avenger",
            "Joe Johnston",
            List.of(new Screening("Apolo kinas", LocalDateTime.of(2025, Month.APRIL, 27, 10, 15))),
            List.of(new Actor("Christ", "Evans", 43)));

    List<Movie> movies = List.of(movie1, movie2);
    //when
    BDDMockito.given(movieService.findById(1)).willReturn(Optional.of(movie1));
    //then
    mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/1", 1))
            .andExpect(status().isUnauthorized());


    Mockito.verify(movieService, times(0)).findById(1L);

  }

  @Test
  @WithMockUser(authorities = "SCOPE_ROLE_ADMIN")
  void addMovie_whenSaveMovieAsAdmin_validMovie() throws Exception {
    Movie newMovie = new Movie("Iron Man",
            "Jon Favreau",
            List.of(new Screening("Apolo kinas", LocalDateTime.of(2025, Month.APRIL, 27, 10, 15))),
            List.of(new Actor("Robert", "Downey Jr.", 60)));
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    String movieJson = objectMapper.writeValueAsString(newMovie);

    BDDMockito.given(movieService.saveMovie(BDDMockito.any())).willReturn(newMovie);
    mockMvc.perform(MockMvcRequestBuilders.post("/api/movies")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(movieJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("title").value("Iron Man"))
            .andExpect(jsonPath("director").value("Jon Favreau"))
            .andExpect(jsonPath("screenings[0].theatersName").value("Apolo kinas"))
            .andExpect(jsonPath("screenings[0].screeningsTime").value("2025-04-27T10:15:00"))
            .andExpect(jsonPath("actors[0].nameActor").value("Robert"))
            .andExpect(jsonPath("actors[0].lastNameActor").value("Downey Jr."))
            .andExpect(jsonPath("actors[0].age").value(60));

    Mockito.verify(movieService, times(1)).saveMovie(any(Movie.class));
  }
}
