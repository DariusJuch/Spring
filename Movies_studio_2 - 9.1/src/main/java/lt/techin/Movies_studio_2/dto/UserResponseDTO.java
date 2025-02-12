package lt.techin.Movies_studio_2.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lt.techin.Movies_studio_2.model.Role;

import java.util.List;

public record UserResponseDTO(long id,
                              @NotNull
                              @Size(min = 3, max = 80, message = "May not be less than 3 and not more than 80 characters")
                              String userName,
                              List<Role> roles
) {


}
