package cl.Proyecto.Prevision.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "previsiones")
public class Prevision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrevision;

    @Column(nullable = false, length = 100)
    private String nombre;

}
