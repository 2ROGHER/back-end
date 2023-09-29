package vollmed.models.medico;

import vollmed.models.medico.direccion.DatosDireccion;
import vollmed.models.medico.especialidad.Especialidad;

public record DatosRespuestaMedico(
        Long id,
        String nombre,
        String email,
        String dni,
        String telefono,
        Especialidad especialidad,
        DatosDireccion direccion
) {
}
