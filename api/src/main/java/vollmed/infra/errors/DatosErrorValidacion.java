package vollmed.infra.errors;

import org.springframework.validation.FieldError;

public record DatosErrorValidacion(String campo, String error) {
    public DatosErrorValidacion(FieldError e) {
        this(e.getField(), e.getDefaultMessage());
    }
}
