package lt.techin.MoviesStudio.service;

import lt.techin.MoviesStudio.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lt.techin.MoviesStudio.repository.MovieRepository;

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
}
