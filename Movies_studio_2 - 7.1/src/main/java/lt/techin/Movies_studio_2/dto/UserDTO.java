package lt.techin.Movies_studio_2.dto;

import jakarta.validation.constraints.Size;

public record UserDTO(long id,
                      @Size(min = 3, max = 80, message = "May not be less than 3 and not more than 80 characters")
                      String userName

) {


}
