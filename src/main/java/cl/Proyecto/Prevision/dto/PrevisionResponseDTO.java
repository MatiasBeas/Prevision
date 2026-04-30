package cl.Proyecto.Prevision.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrevisionResponseDTO {

    private Long id;
    private String nombre;
    private BigDecimal descuento;
}
