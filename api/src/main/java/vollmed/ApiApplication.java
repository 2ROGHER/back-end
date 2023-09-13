package vollmed;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
        AnsiConsole.systemInstall(); // Inicializa Jansi
        System.out.println(Ansi.ansi().fg(Ansi.Color.GREEN).a("Server on: ").reset());
        System.out.println(Ansi.ansi().fg(Ansi.Color.MAGENTA).a("Listen at port: 8080"));

        AnsiConsole.systemUninstall();
    }

}
