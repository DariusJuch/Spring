package lt.techin.Movies_studio_2.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lt.techin.Movies_studio_2.model.Actor;
import lt.techin.Movies_studio_2.model.Screening;
import lt.techin.Movies_studio_2.validation.Director;

import java.util.List;

public record MovieDTO(long id,
                       @NotNull
                       @Size(min = 2, max = 100, message = "May not be less than 2 and not more than 100 characters")
                       String title,
                       @Director
                       String director,
                       List<Screening> screenings,
                       List<Actor> actors) {


}
