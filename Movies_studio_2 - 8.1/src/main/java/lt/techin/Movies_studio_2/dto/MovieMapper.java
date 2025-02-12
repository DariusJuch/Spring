package lt.techin.Movies_studio_2.dto;

import lt.techin.Movies_studio_2.model.Movie;
import org.springframework.data.domain.Page;

import java.util.List;

public class MovieMapper {

  public static List<MovieDTO> toMovieDTOList(List<Movie> movies) {
    List<MovieDTO> result = movies.stream()
            .map(movie -> new MovieDTO(movie.getId(), movie.getTitle(), movie.getDirector(), movie.getScreenings(), movie.getActors()))
            .toList();

    return result;
  }

  public static MovieDTO toMovieDTO(Movie movie) {
    return new MovieDTO(movie.getId(), movie.getTitle(), movie.getDirector(), movie.getScreenings(), movie.getActors());
  }


  public static Movie toMovie(MovieDTO movieDTO) {
    Movie movie = new Movie();
    movie.setTitle(movieDTO.title());
    movie.setDirector(movieDTO.director());
    movie.setScreenings(movieDTO.screenings());
    movie.setActors(movieDTO.actors());

    return movie;
  }

  public static void updateMovieFromDTO(Movie movie, MovieDTO movieDTO) {
    movie.setTitle(movieDTO.title());
    movie.setDirector(movieDTO.director());
    movie.setScreenings(movieDTO.screenings());
    movie.setActors(movieDTO.actors());
  }
  public static Page<MovieDTO> pageMoviesToMoviesDTO(Page<Movie> moviePage){
    return moviePage.map(MovieMapper::toMovieDTO);
  }
}
