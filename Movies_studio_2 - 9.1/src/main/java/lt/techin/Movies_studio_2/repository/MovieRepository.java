package lt.techin.Movies_studio_2.repository;


import lt.techin.Movies_studio_2.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

  boolean existsMoviesByTitle(String title);

  boolean existsMovieByDirector(String director);

  Movie findByTitle(String title);

  Movie findMovieByDirector(String director);

  public List<Movie> getAllMovieByTitle(String title);
}

