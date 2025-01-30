package lt.techin.MoviesStudio.repository;

import lt.techin.MoviesStudio.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {


}
