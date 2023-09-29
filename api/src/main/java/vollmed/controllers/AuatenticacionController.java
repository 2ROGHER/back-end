package vollmed.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vollmed.infra.security.DatosJWTToken;
import vollmed.infra.security.TokenService;
import vollmed.models.users.DatosAutenticacionUsuario;
import vollmed.models.users.Usuario;

@RestController
@RequestMapping("/login")
public class AuatenticacionController {
    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario dto) {
        //version 1.0
        //                Authentication token = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        //                authenticationManager.authenticate(token);
        //                return  ResponseEntity.ok("Success to login");

        //version 1.1
        /**
         * En este caso vamos a generar un JWT token y vamos  a devolver al usuario para que se pueda autentificar.
         * El getPrincipal(), es el usuario ya autenticado que envio sus datos, vale decir que ya me da el principa usuario que ya esta autenticadd
         */
        Authentication authToken = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        var usuarioAutenticado = authenticationManager.authenticate(authToken); //Esto autentica al usuario que quiera iniciar sesion. es decir compara si esta registrado este usuario en la base de datos.
        var  JWTToken = tokenService.generateToken((Usuario) usuarioAutenticado.getPrincipal()); // Toma los campos de ese usuario y genera su hasscode
        return  ResponseEntity.ok(new DatosJWTToken(JWTToken));// Responde con un JWTToken que  se genero para ese usuuario.

    }

}
