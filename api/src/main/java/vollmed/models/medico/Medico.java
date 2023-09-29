package vollmed.models.medico;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import vollmed.models.medico.direccion.Direccion;
import vollmed.models.medico.especialidad.Especialidad;

/**
 * La libreria de lombok nos permite ahorrarnos el codigo de escribir los GETTERS & SETTERs de un objeto en java.
 */
@Table(name="medicos")// Esta anotacion  se emplea para mapear la entidad "medico" en una tabla de datos "medicos"
@Entity(name="Medico")// Esta anotacion indica que la clase "Medico" es una entida JPA, que se debe gestionar en una DB.
@Getter // Esta anotacion proviene de la clase Lombok y genera automaticamente los metodos GETTERS para los campos de la clase "medico"
//@NoArgsConstructor // Genera un contructor si argumentos
//@AllArgsConstructor// Genera un constructor con argumentos.
@EqualsAndHashCode(of="id")
/**
 * También de Lombok, esta anotación genera automáticamente
 * los métodos equals y hashCode basados en el campo id.
 * Esto permite comparar y almacenar instancias de Medico de manera eficiente en colecciones como Set y Map.
 */
public class Medico {
    @Id // se usa para marcar que este campo es la clave primaria
    /**
     * la anotacion @GenerateValue:especifica como se generara el valor de la pk cuando se inserte un nuevo registro en DB.
     * Es decir aqui estamos diciendo que cuando se inserte un nuevo registro se incremente en + 1.
     * Osea admite una generacion automatica de valores  para la pk cuando se inserta un nuevo registro.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String dni;
    private String telefono;
    private Boolean activo;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private Direccion direccion;


    //Constructor with  Lombok

    //getters & setters. with Lombok

    /**
     * Este es el tipico constructor que se usa para trabajar sin los registro
     */
    //    public Medico(String nombre, String email, String dni, Especialidad especialidad, Direccion direccion) {
    //        this.nombre = nombre;
    //        this.email = email;
    //        this.dni = dni;
    //        this.especialidad = especialidad;
    //        this.direccion = new Direccion(null, null, null, null);
    //    }

    // Empleando registros

    public Medico(DatosRegistrarMedico r) {
        this.activo = true;
        this.nombre = r.nombre();
        this.email = r.email();
        this.dni = r.dni();
        this.telefono = r.telefono();
        this.especialidad = r.especialidad();
        this.direccion = new Direccion(r.direccion());
    }

    public Medico() {}

    // Implements method to update
    public void actualizarDatos(DatosActualizarMedico dto) {
        if(dto.nombre() != null) this.nombre = dto.nombre();
        if(dto.dni() != null) this.dni = dto.dni();
        if(dto.direccion() != null) {
            this.direccion = direccion.actualizarDatosDireccion(dto.direccion());
        }
    }

    // Method to desactive the medico entity

    public void desactivarMedico() {
        this.activo = false;
    }
}
