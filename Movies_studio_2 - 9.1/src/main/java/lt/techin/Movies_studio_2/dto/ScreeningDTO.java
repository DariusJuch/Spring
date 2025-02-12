package lt.techin.Movies_studio_2.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public record ScreeningDTO(
        @NotNull
        @Pattern(regexp = "^[A-Z][a-z]+$", message = "Must start with uppercase and can not by null")
        String theatersName,
        @NotNull
        LocalDateTime screeningsTime) {
}
