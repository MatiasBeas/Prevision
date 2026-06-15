package cl.Proyecto.Prevision;

import cl.Proyecto.Prevision.model.Prevision;
import cl.Proyecto.Prevision.repository.PrevisionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Tests unitarios - PrevisionRepository")
class PrevisionRepositoryTest {

    @Autowired
    private PrevisionRepository previsionRepository;

    @Test
    @DisplayName("GIVEN: una prevision nueva WHEN: save THEN: se guarda con un ID generado")
    void shouldSavePrevision() {
        Prevision prevision = new Prevision(null, "Fonasa");

        Prevision guardada = previsionRepository.save(prevision);

        assertThat(guardada.getIdPrevision()).isNotNull();
        assertThat(guardada.getNombre()).isEqualTo("Fonasa");
    }

    @Test
    @DisplayName("GIVEN: una prevision guardada WHEN: findById THEN: la retorna")
    void shouldFindPrevisionById() {
        Prevision guardada = previsionRepository.save(new Prevision(null, "Isapre Cruz Blanca"));

        Optional<Prevision> encontrada = previsionRepository.findById(guardada.getIdPrevision());

        assertThat(encontrada).isPresent();
        assertThat(encontrada.get().getNombre()).isEqualTo("Isapre Cruz Blanca");
    }

    @Test
    @DisplayName("GIVEN: varias previsiones guardadas WHEN: findAll THEN: retorna todas")
    void shouldFindAllPrevisiones() {
        previsionRepository.save(new Prevision(null, "Fonasa"));
        previsionRepository.save(new Prevision(null, "Dipreca"));

        List<Prevision> todas = previsionRepository.findAll();

        assertThat(todas).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("GIVEN: una prevision guardada WHEN: deleteById THEN: ya no se encuentra")
    void shouldDeletePrevision() {
        Prevision guardada = previsionRepository.save(new Prevision(null, "Banmedica"));
        Long id = guardada.getIdPrevision();

        previsionRepository.deleteById(id);

        assertThat(previsionRepository.findById(id)).isEmpty();
    }
}