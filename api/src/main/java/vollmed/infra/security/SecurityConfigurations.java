package vollmed.infra.security;


/**
 * Nota: ahora tenemos que implementar que la solicitud /login sea abierto para todos ya que de lo contraio no
 * nos permite realizar peticiones sin un header con el token como lo seia para las demas solictudes.
 */

import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Esta notacion marca la clase como una configuracion de Spring. y Beans definidos.
@EnableWebSecurity // Esta anotacion habilita la securidad web en la aplicacion.


public class SecurityConfigurations {
    /**
     * Este metodo configura la reglas de seguridad en la aplicion REST
     * Desabilitaos le csrf(), con creacion de sessiones sin estado.
     * @param http
     * @return
     * @throws Exception
     * @Bean: esta anotacion le dice a Spring que existe un metodo en la aplicacion para configuarar
     * y crear un ojbeto(Bean) y que spring debe gestionarlo de la mejor manera.
     */
    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //  Esto desabilita que aparezca la pagina de login cuando se inicia la aplicacion ya que es (STATELESS)
        // En este caso tenemos que decirle a este filtro de que primero agregue mi filtro antes que este filtro para poder
        // abrir la conexion  con el /login  y mantener prohibiod las otras solicitudes
        // Esto verifica que
//        return http.csrf().disable()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .requestMatchers(HttpMethod.POST, "/login")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .addFilter(securityFilter, UsernamePasswordAuthenticationFilter.class) //este metodo verifca que el user tenga una sesion inciada en mi aplcacion
//                .build();
        return  http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .dispatcherTypeMatchers(HttpMethod.POST, DispatcherType.valueOf("/login")).permitAll() // Permite el acceso a /login por POST
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // Agrega tu filtro personalizado
                .build();
    }


    /**
     * ESte metodo proporciona un Bean para gestionar la autenticacion de los usuarios en la aplicacion REST.
     * @param config
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Este metodo define un decodificador de passwords, con un metodo seguro para almacenar contrasenas(BCryptPassowrd())
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
