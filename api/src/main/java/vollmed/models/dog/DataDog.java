package vollmed.models.dog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

//TODO: implement validations with Bean.
public record DataDog(
        int id,
        String name,
        int age,
        String color
) {
}
