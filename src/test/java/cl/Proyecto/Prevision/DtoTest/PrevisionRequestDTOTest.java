package cl.Proyecto.Prevision.DtoTest;

import cl.Proyecto.Prevision.dto.PrevisionRequestDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests unitarios - PrevisionRequestDTO")
class PrevisionRequestDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("GIVEN: nombre valido WHEN: validar THEN: sin errores")
    void shouldPassWhenNombreIsValid() {
        PrevisionRequestDTO dto = new PrevisionRequestDTO("Fonasa");

        Set<ConstraintViolation<PrevisionRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("GIVEN: nombre vacio WHEN: validar THEN: error de validacion")
    void shouldFailWhenNombreIsEmpty() {
        PrevisionRequestDTO dto = new PrevisionRequestDTO("");

        Set<ConstraintViolation<PrevisionRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
    }

    @Test
    @DisplayName("GIVEN: nombre null WHEN: validar THEN: error de validacion")
    void shouldFailWhenNombreIsNull() {
        PrevisionRequestDTO dto = new PrevisionRequestDTO(null);

        Set<ConstraintViolation<PrevisionRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
    }

    @Test
    @DisplayName("GIVEN: nombre solo espacios WHEN: validar THEN: error de validacion")
    void shouldFailWhenNombreIsBlank() {
        PrevisionRequestDTO dto = new PrevisionRequestDTO("   ");

        Set<ConstraintViolation<PrevisionRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
    }
}