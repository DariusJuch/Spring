package lt.techin.Movies_studio_2.dto;

import lt.techin.Movies_studio_2.model.Actor;
import lt.techin.Movies_studio_2.model.Movie;

import java.util.List;

public class ActorMapper {

  public static List<ActorDTO> toActorDTOList(List<Actor> actors) {
    List<ActorDTO> result = actors.stream()
            .map(actor -> new ActorDTO(actor.getId(), actor.getNameActor(), actor.getLastNameActor(), actor.getAge()))
            .toList();

    return result;
  }

  public static ActorDTO toActorDTO(Actor actor) {
    return new ActorDTO(actor.getId(), actor.getNameActor(), actor.getLastNameActor(), actor.getAge());
  }

  public static Actor toActor(ActorDTO actorDTO) {
    Actor actor = new Actor();
    actor.setNameActor(actorDTO.nameActor());
    actor.setLastNameActor(actorDTO.lastNameActor());


    return actor;
  }


}
