package com.example.ResourceManagement.controller;


import com.example.ResourceManagement.model.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MovieController {
  public List<Movie> movies = new ArrayList<>();

  @GetMapping("/movies")
  public ResponseEntity<List<Movie>> getMovies() {
    return ResponseEntity.ok(movies);

  }

  @GetMapping("/movies/{index}")
  public ResponseEntity<Movie> getMovie(@PathVariable int index) {
    if (index > movies.size() - 1) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(movies.get(index));
  }

  @GetMapping("/movies/search")
  public ResponseEntity<List<Movie>> searchMovies(@RequestParam(value = "title") String title) {
    List<Movie> filteredMovies = movies.stream()
            .filter(movie -> movie.getTitle().toLowerCase().contains(title.toLowerCase()))
            .collect((Collectors.toList()));
    if (filteredMovies.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(filteredMovies, HttpStatus.OK);
  }


  @PostMapping("/movies")
  public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
    if (movie.getTitle().isEmpty() || movie.getDirector().isEmpty() || movie.getTitle() == null || movie.getDirector() == null) {
      return ResponseEntity.badRequest().build();
    }
    movies.add(movie);
    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{index}")
                            .buildAndExpand(movies.size() - 1)
                            .toUri())
            .body(movie);
  }

  @PutMapping("/movies/{index}")
  public ResponseEntity<Movie> updateMovie(@PathVariable int index, @RequestBody Movie movie) {
    if (movie.getTitle().isEmpty() || movie.getDirector().isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    if (index > movies.size() - 1) {
      Movie movieDb = movies.get(index);

      movieDb.setTitle(movie.getTitle());
      movieDb.setDirector(movie.getDirector());

      return ResponseEntity.ok(movieDb);
    }
    movies.add(movie);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{index}")
                            .buildAndExpand(movies.size())
                            .toUri())
            .body(movie);

  }

  @DeleteMapping("/movie/{index}")
  public ResponseEntity<Void> deleteBook(@PathVariable int index) {
    if (index > movies.size() - 1) {
      return ResponseEntity.notFound().build();
    }
    movies.remove(index);
    return ResponseEntity.noContent().build();
  }
}







