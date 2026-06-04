package cl.Proyecto.Prevision.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa los datos de salida de una Prevision")
public class PrevisionResponseDTO {

    @Schema(description = "ID único de cada prevision registrada en la base de datos", example = "1")
    private Long idPrevision;
    @Schema(description = "Nombre Oficial y original", example = "AFP Provida")
    private String nombre;
}
