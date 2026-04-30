package cl.Proyecto.Prevision.config;

import cl.Proyecto.Prevision.modelo.Prevision;
import cl.Proyecto.Prevision.repository.PrevisionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final PrevisionRepository previsionRepository;

    @Override
    public void run(String...args){
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
