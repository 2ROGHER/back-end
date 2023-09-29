package vollmed.controllers;
/**
 * Nota: LA CARPTETA DOMAIN TIENE LA MISMA FUNCIONALIDAD QUE LA CARPETA MODELS
 */

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import vollmed.models.medico.direccion.DatosDireccion;
import vollmed.models.medico.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/medicos")

public class MedicoController {

    /**
     * la notacion de @Autowired se usa para realizar inyeccion de dependencias.
     * Permite que Spring inyecte automaticamente "instancias" de clases en las propiedades, metodo o contrucotr de tu
     * clase actual.
     * Evita que creees manualmente esas intancias dentro de tu clase.
     */

    @Autowired
    private MedicoRepository medicoRepository;

    @GetMapping("/test-medicos")
    // @RequestBody es lo mismo que el (req, res) => que se hace con java.
    public DatosRegistrarMedico showMedico(DatosRegistrarMedico data) {
        System.out.println("Returning all data from DatosRegistrarMedico from records");
        // null, porque no se esta enviando nada con el body de request
        return data;
    }

    @GetMapping("/all-medicos")
    public List<Medico> listadoMedicos() {
        return medicoRepository.findAll();
    }

    @GetMapping("/medicos-activos")
    public ResponseEntity<Page<DatosListadoMedico>> listarMedicosActivos(@PageableDefault(size = 2) Pageable page) {
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(page).map(DatosListadoMedico::new));
    }

    @GetMapping("/medicos-by-page")
    public Page<DatosListadoMedico> listarMedicos(@PageableDefault(size = 1) Pageable page) {
        return medicoRepository.findAll(page).map(DatosListadoMedico::new);
    }

//    @PostMapping
//    public void registerMedico(@RequestBody @Valid DatosRegistrarMedico data) {
//        System.out.println("Ejecutando el registro de datos en la DB");
//        medicoRepository.save(new Medico(data));
//    }

    // Post v2.
    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistrarMedico data, UriComponentsBuilder uri) {
        Medico m = medicoRepository.save(new Medico(data));
        DatosRespuestaMedico response = new DatosRespuestaMedico(
                m.getId(),
                m.getNombre(),
                m.getEmail(),
                m.getDni(),
                m.getTelefono(),
                m.getEspecialidad(),
                new DatosDireccion(
                        m.getDireccion().getCalle(),
                        m.getDireccion().getDistrito(),
                        m.getDireccion().getCiudad(),
                        m.getDireccion().getNumero(),
                        m.getDireccion().getComplemento()
                )
        );

        URI url = uri.path("/medicos/{id}").buildAndExpand(m.getId()).toUri();
        return ResponseEntity.created(url).body(response);
    }
    // Update db with spring

    @PutMapping
    @Transactional
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico dto) {
        Medico m = medicoRepository.getReferenceById(dto.id());
        m.actualizarDatos(dto);
        return ResponseEntity.ok(new DatosRespuestaMedico(
                m.getId(),
                m.getNombre(),
                m.getDni(),
                m.getEmail(),
                m.getTelefono(),
                m.getEspecialidad(),
                new DatosDireccion(
                        m.getDireccion().getCalle(),
                        m.getDireccion().getDistrito(),
                        m.getDireccion().getCiudad(),
                        m.getDireccion().getNumero(),
                        m.getDireccion().getComplemento()
                )
        ));
    }

    //Delete for the medico entity
    @DeleteMapping("/{id}") // Esto indica que se va a ingresar un {id} en la ruta HTTP
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id) {
        List<Medico> medicos = medicoRepository.findAll();
        medicos.forEach((m) -> {
            if(m.getId() != id) {
                System.out.println(m);
                // TODO: implement athis valid
                medicoRepository.save(m);
            }
        });
       return ResponseEntity.ok(id);

    }

    //Find a medico by {id}
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> medicoPorId(@PathVariable Long id) {
        Medico m = medicoRepository.getReferenceById(id);
        var datosMedico =  new DatosRespuestaMedico(
                m.getId(),
                m.getNombre(),
                m.getDni(),
                m.getEmail(),
                m.getTelefono(),
                m.getEspecialidad(),
                new DatosDireccion(
                        m.getDireccion().getCalle(),
                        m.getDireccion().getDistrito(),
                        m.getDireccion().getCiudad(),
                        m.getDireccion().getNumero(),
                        m.getDireccion().getComplemento()
                )
        );
        return ResponseEntity.ok(datosMedico);
    }
}
