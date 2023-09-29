package vollmed.models.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Esta interfaz se usa para acceder y realizar operaaciones con la BD relacionada con al entidad "medico"
 * Definimos los metodos que estaran disponibles para interactuar con la base de datos para la entidad "Medico"
 * La interfaz JpaRepository proporciona una serie de funciones comunes para realizar operaciones con la base de datos.
 * Esta es una capa de abstraccion entre la DB y la aplicacion, cuyo finaliad es proporcionar una forma sencilla
 * de interactuar con la base de datos, abstrayendo la complejidad de las consultas SQL y las operaciones  de BD subyacente.
 */
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findByActivoTrue(Pageable page);
}
