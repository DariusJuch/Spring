package lt.techin.Movies_studio_2.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotNull
        @Size(min = 3, max = 80, message = "May not be less than 3 and not more than 80 characters")
        String userName,
        @NotNull
        String password


) {


}
