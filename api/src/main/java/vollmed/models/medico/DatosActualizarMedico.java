package vollmed.models.medico;

import vollmed.models.medico.direccion.DatosDireccion;

public record DatosActualizarMedico(
        Long id,
        String nombre,
        String dni,
        DatosDireccion direccion
) {
}
