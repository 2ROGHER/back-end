package vollmed.controllers;

import org.springframework.web.bind.annotation.GetMapping;
/**
 *  esta linea se usa par mapear solicitudes http a metodos controladores.
 */
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Esta line se usa para decirle a spring que vamos  a declarar que esta clase es un controladore REST
 */
import org.springframework.web.bind.annotation.RestController;

@RestController // Marcamos esta clase como un controlador REST. y que sus metodos en lugar de devolver vistas
// devolveran respuesta HTTP.
@RequestMapping(value = "/test")
// marcamos a todos los metodos controladores de esta clase con la ruta base "/test"
public class HelloController {

    @GetMapping // Con esto marcamos que este metodo es un controlador de solicitures HTTP tipo get
                // indica que este metodo respondera a las solictiudaes  con GET.
    public String helloWorld() {
        return "Hola mundo estoy en Spring, aprendiendo mucho y haciendo back-ends de calidad!";
    }
}
