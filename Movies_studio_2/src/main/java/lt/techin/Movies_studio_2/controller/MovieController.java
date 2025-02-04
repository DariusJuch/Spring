package lt.techin.Movies_studio_2.controller;


import jakarta.validation.Valid;
import lt.techin.Movies_studio_2.model.Movie;
import lt.techin.Movies_studio_2.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MovieController {

  private final MovieService movieService;

  @Autowired
  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  @GetMapping("/movies")
  public ResponseEntity<List<Movie>> getMovies() {
    return ResponseEntity.ok(movieService.findAllMovies());
  }

  @GetMapping("/movies/{id}")
  public ResponseEntity<Movie> getMovie(@PathVariable long id) {
    Optional<Movie> foundMovie = movieService.findById(id);

    if (foundMovie.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(foundMovie.get());
  }

  @PostMapping("/movies")
  public ResponseEntity<?> saveMovie(@Valid @RequestBody Movie movie) {
//    if (movie.getTitle().isEmpty() || movie.getDirector().isEmpty()) {
//      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie title and Director cannot be empty");
//    }
    Movie saveMovie = movieService.saveMovie(movie);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(saveMovie.getId())
                            .toUri())
            .body(movie);
  }

  @PutMapping("/movies/{id}")
  public ResponseEntity<?> updateMovie(@PathVariable long id, @RequestBody Movie movie) {
    if (movie.getTitle().isEmpty() || movie.getDirector().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    if (movieService.existMovieById(id)) {

      Movie movieDb = movieService.findById(id).get();

      movieDb.setTitle(movie.getTitle());
      movieDb.setDirector(movie.getDirector());
      movieDb.setScreenings(movie.getScreenings());
      movieDb.setActors(movie.getActors());

      return ResponseEntity.ok(movieService.saveMovie(movieDb));
    }
    Movie saveMovie = movieService.saveMovie(movie);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .replacePath("/api/movies/{id}")
                            .buildAndExpand(saveMovie.getId())
                            .toUri())
            .body(movie);
  }

  @DeleteMapping("/movies/{id}")
  public ResponseEntity<Void> deleteMovie(@PathVariable long id) {
    if (!movieService.existMovieById(id)) {
      return ResponseEntity.notFound().build();
    }
    movieService.deleteMovieById(id);
    return ResponseEntity.noContent().build();
  }


}
