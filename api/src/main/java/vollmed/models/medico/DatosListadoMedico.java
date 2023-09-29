package vollmed.models.medico;

public record DatosListadoMedico (
    String nombre,
    String email,
    String dni,
    String especialidad
) {
    public DatosListadoMedico(Medico m) {
        this(m.getNombre(), m.getEmail(), m.getDni(), m.getEspecialidad().toString());
    }
}
