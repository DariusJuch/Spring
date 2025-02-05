package lt.techin.Movies_studio_2.service;

import lt.techin.Movies_studio_2.model.Actor;
import lt.techin.Movies_studio_2.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorService {

  private final ActorRepository actorRepository;

  @Autowired
  public ActorService(ActorRepository actorRepository) {
    this.actorRepository = actorRepository;
  }

  public Actor saveActor(Actor actor) {
    return actorRepository.save(actor);
  }

  public List<Actor> findAllActors() {
    return actorRepository.findAll();
  }
}
