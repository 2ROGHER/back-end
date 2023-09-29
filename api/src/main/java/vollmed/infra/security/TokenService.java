package vollmed.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vollmed.models.users.Usuario;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;

// Nota: Aquellas apliaciones que usan un login y constrenaia son denomidas "STATEFULL"
@Service
public class TokenService {

    // NOTE: Tener en cuenta que algunos IDs no tienen acceso a las variables  de entorno de mi sistema.
    @Value("${api.security.secret}") // con esta anotacion le digo que quiereo extraer el valor que hay en esa variable.
    private String apiSecret;

    public String generateToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("voll med")
                    .withSubject(usuario.getUsername())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generateFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw  new RuntimeException(e.getMessage());
        }
    }

    private Instant generateFechaExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }

    // el medoto getSubject se refiere a que usuario esta siendo  asigando o para quien se ha generado el token previamente
    public String getSubject(String token) {
        if(token == null){
            throw new RuntimeException("token invalid or null");
        }

        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
             verifier =  JWT.require(algorithm)
                    .withIssuer("voll med")
                    .build()
                    .verify(token);
            verifier.getSubject();
        } catch (JWTVerificationException e) {
            throw  new RuntimeException(e.getMessage());
        }
        if(verifier.getSubject() == null) {
            throw  new RuntimeException("Verificador del user invalid");
        }
        return verifier.getSubject();
    }

    // N

}
