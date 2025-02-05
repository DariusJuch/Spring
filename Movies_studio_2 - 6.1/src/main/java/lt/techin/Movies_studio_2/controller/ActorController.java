package lt.techin.Movies_studio_2.controller;


import jakarta.validation.Valid;
import lt.techin.Movies_studio_2.dto.ActorDTO;
import lt.techin.Movies_studio_2.dto.ActorMapper;
import lt.techin.Movies_studio_2.dto.MovieMapper;
import lt.techin.Movies_studio_2.model.Actor;
import lt.techin.Movies_studio_2.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ActorController {

  private final ActorService actorService;

  @Autowired
  public ActorController(ActorService actorService) {
    this.actorService = actorService;
  }

  @GetMapping("/actors")
  public ResponseEntity<List<ActorDTO>> getMovies() {
    return ResponseEntity.ok(ActorMapper.toActorDTOList(actorService.findAllActors()));
  }

  @PostMapping("/actors")
  public ResponseEntity<?> addActor(@Valid @RequestBody ActorDTO actorDTO) {
//    if (actor.getNameActor().isEmpty() || actor.getLastNameActor().isEmpty()) {
//      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name and last name cannot be empty");
//    }

    Actor savedActor = actorService.saveActor(ActorMapper.toActor(actorDTO));

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedActor.getId())
                            .toUri())
            .body(ActorMapper.toActorDTO(savedActor));
  }
}

