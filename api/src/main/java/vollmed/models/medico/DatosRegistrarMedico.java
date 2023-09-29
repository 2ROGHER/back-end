package vollmed.models.medico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import vollmed.models.medico.direccion.DatosDireccion;
import vollmed.models.medico.especialidad.Especialidad;

/**
 * Estae es un tipo de DS que esta dinesiando para objetos de solo lectura que tienen propiedades inmutables.
 * Es decir propiedades que no canbian luego de la creacion.
 * Y no requieren logica adicional. (la diferencia de al interfaces es que no poseen logica solo propiedades).
 * Las propieades dentro de un record son declarados como "final" es decir constantes.
 * Al crear un record con ello se crean metods utiles para trabajar con ellos como (equal(), hashCode(), toStrintg())
 * No es necesario acceder a las propiedades mediante los getter(), son estaticos y de facil lectura, (solo lectura).
 */
public record DatosRegistrarMedico(
        Long id,
        @NotBlank
        String nombre,
        @NotBlank
        String email,
        @NotBlank
        String dni,
        @NotBlank
        String telefono,
        @NotNull(message = "The field Especialidad must not be null")
        Especialidad especialidad,
        @NotNull(message="The field direccion must not be  null")
        DatosDireccion direccion
) {
}
