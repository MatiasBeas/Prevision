package cl.Proyecto.Prevision.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos requeridos para la creacion y actualizacion de las Previsiones")
public class PrevisionRequestDTO {

    @NotBlank(message = "El nombre no puede estar vacio")
    @Schema(description = "Nombre Oficial y original", example = "AFP Provida")
    private String nombre;


}
