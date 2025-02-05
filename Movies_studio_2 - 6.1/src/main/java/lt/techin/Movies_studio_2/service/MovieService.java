package lt.techin.Movies_studio_2.service;


import lt.techin.Movies_studio_2.model.Movie;
import lt.techin.Movies_studio_2.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

  private final MovieRepository movieRepository;

  @Autowired
  public MovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public List<Movie> findAllMovies() {
    return movieRepository.findAll();
  }

  public Optional<Movie> findById(long id) {
    return movieRepository.findById(id);
  }

  public Movie saveMovie(Movie movie) {
    return movieRepository.save(movie);
  }

  public boolean existMovieById(long id) {
    return movieRepository.existsById(id);
  }

  public void deleteMovieById(long id) {
    movieRepository.deleteById(id);
  }

  public boolean existMovieByTitle(String title) {
    return movieRepository.existsMoviesByTitle(title);
  }

  public boolean existMovieByDirector(String director) {
    return movieRepository.existsMovieByDirector(director);
  }

  public Movie findMovieByTitle(String title) {
    return movieRepository.findByTitle(title);
  }

  public Movie findMovieByDirector(String director) {
    return movieRepository.findMovieByDirector(director);
  }

  public Page<Movie> findAllMovies(int page, int size) {

    Pageable pageable = PageRequest.of(page, size);
    return movieRepository.findAll(pageable);
  }
}
