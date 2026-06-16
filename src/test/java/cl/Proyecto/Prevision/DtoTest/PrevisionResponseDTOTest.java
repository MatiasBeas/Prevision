package cl.Proyecto.Prevision.DtoTest;

import cl.Proyecto.Prevision.dto.PrevisionResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests unitarios - PrevisionResponseDTO")
class PrevisionResponseDTOTest {

    @Test
    @DisplayName("GIVEN: datos validos WHEN: crear ResponseDTO THEN: campos correctos")
    void shouldCreateResponseDTOWithCorrectFields() {
        PrevisionResponseDTO dto = new PrevisionResponseDTO(1L, "Fonasa");

        assertThat(dto.getIdPrevision()).isEqualTo(1L);
        assertThat(dto.getNombre()).isEqualTo("Fonasa");
    }

    @Test
    @DisplayName("GIVEN: ResponseDTO vacio WHEN: usar setters THEN: campos actualizados")
    void shouldSetFieldsCorrectly() {
        PrevisionResponseDTO dto = new PrevisionResponseDTO();
        dto.setIdPrevision(2L);
        dto.setNombre("Isapre Cruz Blanca");

        assertThat(dto.getIdPrevision()).isEqualTo(2L);
        assertThat(dto.getNombre()).isEqualTo("Isapre Cruz Blanca");
    }
}