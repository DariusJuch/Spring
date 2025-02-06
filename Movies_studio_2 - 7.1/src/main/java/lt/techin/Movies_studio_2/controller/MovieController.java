package lt.techin.Movies_studio_2.controller;


import jakarta.validation.Valid;
import lt.techin.Movies_studio_2.dto.MovieDTO;
import lt.techin.Movies_studio_2.dto.MovieMapper;
import lt.techin.Movies_studio_2.model.Movie;
import lt.techin.Movies_studio_2.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
  public ResponseEntity<List<MovieDTO>> getMovies() {
    return ResponseEntity.ok(MovieMapper.toMovieDTOList(movieService.findAllMovies()));
  }

  @GetMapping("/movies/{id}")
  public ResponseEntity<MovieDTO> getMovie(@PathVariable long id) {
    Optional<Movie> foundMovie = movieService.findById(id);

    if (foundMovie.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(MovieMapper.toMovieDTO(foundMovie.get()));
  }

  @PostMapping("/movies")
  public ResponseEntity<?> saveMovie(@Valid @RequestBody MovieDTO movieDTO) {
//    if (movieService.existMovieByTitle(movieDTO.title())) {
//      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie already exists!");
//    }
    boolean alreadyExists = movieService.getAllMovieByName(movieDTO.title())
            .stream().anyMatch(movie -> movie.getDirector().equalsIgnoreCase(movieDTO.director()));
    if (alreadyExists) {
      return ResponseEntity.badRequest().body("Movie with provided name and director already exists");
    }
    Movie saveMovie = movieService.saveMovie(MovieMapper.toMovie(movieDTO));

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(saveMovie.getId())
                            .toUri())
            .body(MovieMapper.toMovieDTO(saveMovie));
  }

  @PutMapping("/movies/{id}")
  public ResponseEntity<?> updateMovie(@PathVariable long id, @Valid @RequestBody MovieDTO movieDTO) {
//    if (movie.getTitle().isEmpty() || movie.getDirector().isEmpty()) {
//      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//    }
    if (movieService.existMovieById(id)) {

      Movie movieDb = movieService.findById(id).get();
//
//      movieDb.setTitle(movie.getTitle());
//      movieDb.setDirector(movie.getDirector());
//      movieDb.setScreenings(movie.getScreenings());
//      movieDb.setActors(movie.getActors());
//
//      return ResponseEntity.ok(movieService.saveMovie(movieDb));


//    }
      MovieMapper.updateMovieFromDTO(movieDb, movieDTO);
      movieService.saveMovie(movieDb);
      return ResponseEntity.ok(movieDTO);
    }
    Movie savedMovie = movieService.saveMovie(MovieMapper.toMovie(movieDTO));

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .replacePath("/api/movies/{id}")
                            .buildAndExpand(savedMovie.getId())
                            .toUri())
            .body(movieDTO);
  }

  @DeleteMapping("/movies/{id}")
  public ResponseEntity<Void> deleteMovie(@PathVariable long id) {
    if (!movieService.existMovieById(id)) {
      return ResponseEntity.notFound().build();
    }
    movieService.deleteMovieById(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/movies/pagination")
  public ResponseEntity<Page<MovieDTO>> getMoviesPage(@RequestParam int page,
                                                      @RequestParam int size) {
    Page<Movie> pageMovie = movieService.findAllMovies(page, size);
    Page<MovieDTO> movieDTOPage = MovieMapper.pageMoviesToMoviesDTO(pageMovie);
    return ResponseEntity.ok(movieDTOPage);
  }


}
