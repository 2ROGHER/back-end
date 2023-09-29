package vollmed.models.medico.direccion;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Direccion {

    private String calle;
    private String distrito;
    private String ciudad;
    private String numero;
    private String complemento;

    public Direccion(DatosDireccion d) {
        this.calle = d.calle();
        this.distrito = d.distrito();
        this.ciudad = d.ciudad();
        this.numero = d.numero();
        this.complemento = d.complemento();
    }

    public Direccion actualizarDatosDireccion(DatosDireccion d) {
        this.calle = d.calle();
        this.distrito = d.distrito();
        this.ciudad = d.ciudad();
        this.numero = d.numero();
        this.complemento = d.complemento();
        return this;
    }
}
