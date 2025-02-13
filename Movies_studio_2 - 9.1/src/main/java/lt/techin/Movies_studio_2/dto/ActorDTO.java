package lt.techin.Movies_studio_2.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lt.techin.Movies_studio_2.validation.ActorName;
import lt.techin.Movies_studio_2.validation.LastNameActor;

public record ActorDTO(long id,
                       @Size(min = 2, max = 20, message = "May not be less than 3 and not more than 20 characters")
                       @ActorName
                       String nameActor,
                       @LastNameActor
                       @Size(min = 2, max = 50, message = "May not be less than 2 and not more than 50 characters")
                       String lastNameActor,
                       long age) {
}
