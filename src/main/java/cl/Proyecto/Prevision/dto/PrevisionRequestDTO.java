package cl.Proyecto.Prevision.dto;

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
public class PrevisionRequestDTO {

    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;

    @NotNull(message = "El descuento es oobligatorio")
    @Positive(message = "El descuento debe ser mayor a 0")
    private BigDecimal descuento;
}
