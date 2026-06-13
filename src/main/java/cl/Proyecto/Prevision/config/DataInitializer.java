package cl.Proyecto.Prevision.config;

import cl.Proyecto.Prevision.model.Prevision;
import cl.Proyecto.Prevision.model.Role;
import cl.Proyecto.Prevision.model.User;
import cl.Proyecto.Prevision.repository.PrevisionRepository;
import cl.Proyecto.Prevision.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final PrevisionRepository previsionRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void run(String...args){
        // Crear usuario si no existe
        if (userRepository.findByUsername("Maty").isEmpty()) {
            User user = new User();
            user.setUsername("Maty");
            user.setPassword(passwordEncoder.encode("1234"));
            user.setRole(Role.ADMIN);
            userRepository.save(user);
            log.info(">>> DataInitializer: Usuario Maty creado correctamente.");
        }
        // Si ya hay datos en la BD no insertamos nada
        if (previsionRepository.count() > 0) {
            log.info(">>> DataInitializer: la BD ya tiene datos, se omite la carga inicial.");
            return;
        }

        log.info(">>> DataInitializer: BD vacía detectada, insertando datos de prueba...");

        previsionRepository.save(new Prevision(null, "Fonasa"));
        previsionRepository.save(new Prevision(null, "Isapre Cruz Blanca"));
        previsionRepository.save(new Prevision(null, "Isapre Banmédica"));
        previsionRepository.save(new Prevision(null, "Isapre Colmena"));
        previsionRepository.save(new Prevision(null, "Dipreca"));

        log.info(">>> DataInitializer: {} previsiones insertadas correctamente.",
                previsionRepository.count());
    }
}
