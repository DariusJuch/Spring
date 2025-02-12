package lt.techin.Movies_studio_2.MovieControllerTests;


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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.regex.Matcher;

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
  @WithMockUser
  void getMovies_whenFindAll_thenReturnAllAnd200() throws Exception {
    //given
    Movie movie1 = new Movie("Iron Man",
            "Jon Favreau",
            List.of(),
            List.of(new Actor("Robert", "Downey Jr.", 60)));

    Movie movie2 = new Movie("Captain America: The First Avenger",
            "Joe Johnston",
            List.of(),
//            List.of(new Screening("Apolo kinas", "(2025-03-27 10:15:20)" ),
            List.of(new Actor("Christ", "Evans", 43)));

    List<Movie> movies = List.of(movie1, movie2);

    BDDMockito.given(movieService.findAllMovies()).willReturn(movies);

    //when
    mockMvc.perform(MockMvcRequestBuilders.get("/api/movies"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("lenght").value(2))
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
            .andExpect(jsonPath("[0].actors.[0].age").value(43));

    Mockito.verify(movieService, times(1)).findAllMovies();

  }


}
