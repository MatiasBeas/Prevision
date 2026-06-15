package cl.Proyecto.Prevision;

import cl.Proyecto.Prevision.Service.PrevisionService;
import cl.Proyecto.Prevision.dto.PrevisionRequestDTO;
import cl.Proyecto.Prevision.dto.PrevisionResponseDTO;
import cl.Proyecto.Prevision.model.Prevision;
import cl.Proyecto.Prevision.repository.PrevisionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios - PrevisionService")
class PrevisionServiceTest {

    @Mock
    private PrevisionRepository previsionRepository;

    @InjectMocks
    private PrevisionService previsionService;

    @Test
    @DisplayName("GIVEN: previsiones en BD WHEN: obtenerTodos THEN: retorna lista con todos")
    void shouldReturnAllPrevisiones() {
        // GIVEN
        Prevision p1 = new Prevision(1L, "Fonasa");
        Prevision p2 = new Prevision(2L, "Dipreca");
        when(previsionRepository.findAll()).thenReturn(List.of(p1, p2));

        // WHEN
        List<PrevisionResponseDTO> resultado = previsionService.obtenerTodos();

        // THEN
        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Fonasa");
        assertThat(resultado.get(1).getNombre()).isEqualTo("Dipreca");
        verify(previsionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("GIVEN: ID existente WHEN: obtenerPorId THEN: retorna el DTO correcto")
    void shouldReturnPrevisionById_whenExists() {
        // GIVEN
        Prevision prevision = new Prevision(1L, "Fonasa");
        when(previsionRepository.findById(1L)).thenReturn(Optional.of(prevision));

        // WHEN
        Optional<PrevisionResponseDTO> resultado = previsionService.obtenerPorId(1L);

        // THEN
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Fonasa");
        assertThat(resultado.get().getIdPrevision()).isEqualTo(1L);
        verify(previsionRepository).findById(1L);
    }

    @Test
    @DisplayName("GIVEN: ID inexistente WHEN: obtenerPorId THEN: retorna Optional vacío")
    void shouldReturnEmpty_whenPrevisionNotFound() {
        // GIVEN
        when(previsionRepository.findById(99L)).thenReturn(Optional.empty());

        // WHEN
        Optional<PrevisionResponseDTO> resultado = previsionService.obtenerPorId(99L);

        // THEN
        assertThat(resultado).isEmpty();
        verify(previsionRepository).findById(99L);
    }

    @Test
    @DisplayName("GIVEN: un DTO valido WHEN: guardar THEN: persiste y retorna el DTO con ID")
    void shouldSavePrevision() {
        // GIVEN
        PrevisionRequestDTO dto = new PrevisionRequestDTO("Isapre Colmena");
        Prevision guardada = new Prevision(1L, "Isapre Colmena");
        when(previsionRepository.save(any(Prevision.class))).thenReturn(guardada);

        // WHEN
        PrevisionResponseDTO resultado = previsionService.guardar(dto);

        // THEN
        assertThat(resultado.getIdPrevision()).isEqualTo(1L);
        assertThat(resultado.getNombre()).isEqualTo("Isapre Colmena");
        verify(previsionRepository).save(any(Prevision.class));
    }

    @Test
    @DisplayName("GIVEN: ID existente WHEN: actualizar THEN: actualiza y retorna el DTO")
    void shouldUpdatePrevision_whenExists() {
        // GIVEN
        Prevision existente = new Prevision(1L, "Fonasa");
        PrevisionRequestDTO dto = new PrevisionRequestDTO("Fonasa Actualizado");
        Prevision actualizada = new Prevision(1L, "Fonasa Actualizado");
        when(previsionRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(previsionRepository.save(any(Prevision.class))).thenReturn(actualizada);

        // WHEN
        Optional<PrevisionResponseDTO> resultado = previsionService.actualizar(1L, dto);

        // THEN
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Fonasa Actualizado");
        verify(previsionRepository).findById(1L);
        verify(previsionRepository).save(any(Prevision.class));
    }

    @Test
    @DisplayName("GIVEN: ID inexistente WHEN: actualizar THEN: retorna Optional vacío")
    void shouldReturnEmpty_whenUpdateNotFound() {
        // GIVEN
        when(previsionRepository.findById(99L)).thenReturn(Optional.empty());

        // WHEN
        Optional<PrevisionResponseDTO> resultado = previsionService.actualizar(99L, new PrevisionRequestDTO("X"));

        // THEN
        assertThat(resultado).isEmpty();
        verify(previsionRepository, never()).save(any());
    }

    @Test
    @DisplayName("GIVEN: ID existente WHEN: eliminar THEN: llama deleteById una vez")
    void shouldDeletePrevision() {
        // GIVEN
        doNothing().when(previsionRepository).deleteById(1L);

        // WHEN
        previsionService.eliminar(1L);

        // THEN
        verify(previsionRepository, times(1)).deleteById(1L);
    }
}