package vollmed.infra.security;

/**
 * Nota:
 *
 *
 * Autenticacion: es al forma en que validamos quienes decimos que somos. hay metodos que validan los datos de la bd con el request
 *
 *
 * Autorizacon: es darle ciertos privilegios al usuario autenticado para que haga operaciones en la aplicacion.
 */

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.annotations.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import vollmed.models.users.UsuarioRepository;

import java.io.IOException;

@Component // este es el la anotacion mas generico para definir un component de spring que lo agrega para spring.
// En spring todos son componentes esto se se dividen en tipos especificos para saver mas "Spring core"
// .
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("El filtro esta funcionando");
        // En este punto, no se muetran la lista, dado que no esta funcinando la cadena de filtros
        // es decir que el resuqest o la solictud no llega hasta el contorller.
        // Entonces;
        // Tenemos que llama al filtro siguiete "doFilter" para que llame al siguiente filtro para que llegue al controller.
        // AHORA: Tenemos que validad el token que se devuelve
        // para esto tenemos que obtener el token de los headers.
        var tokenHeader = request.getHeader("Authorization");

        System.out.println(tokenHeader); // vemos si se esta capturando el token del header.

        // Ahora tenemos que hacer un handler de este token para evitar los errores.
        if(tokenHeader != null) {
            var token = tokenHeader.replace("Bearer ", "");
            // Luego verificamos que el subject esta loheado en mi sistema (usuario)
            System.out.println(tokenService.getSubject(token)); // verificamos que este logheado el subjecto (usuario)
            var subject = tokenService.getSubject(token);
            if(subject != null) {
                // esto me dice que el token es valido
                //Entonces lo que hacemos es forzar un inicio  de sesion en mi sistema o aplicacion
                // Y le decimos a spring que este /login es valido para mi
                var usuario = usuarioRepository.findByUsername(subject);
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()); // con esto forzamos el inicio de sesion.
                SecurityContextHolder.getContext().setAuthentication(authentication);// seteamos manualmente esta configuracion, es decir que para los otros solicitudes ya esta "autorizado"
            }
        }

        //Este codigo nos pemite que la solicitud llegue hasta el controler.

        filterChain.doFilter(request, response);


    }
}
