package cl.Proyecto.Prevision.Controller;

import cl.Proyecto.Prevision.Service.PrevisionService;
import cl.Proyecto.Prevision.dto.PrevisionRequestDTO;
import cl.Proyecto.Prevision.dto.PrevisionResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/previsiones")
@RequiredArgsConstructor
public class PrevisionController {
    private final PrevisionService previsionService;

    //-----------------BUSCAR PREVISIONES DE DISTINTAS FORMAS----------
    @GetMapping
    public ResponseEntity<List<PrevisionResponseDTO>> obtenerTodos(){
        return ResponseEntity.ok(previsionService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrevisionResponseDTO> obtenerPorId(@PathVariable Long id){
        return previsionService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //-----------------GUARDAR PREVISION----------
    @PostMapping
    public ResponseEntity<PrevisionResponseDTO> crear(
            @Valid @RequestBody PrevisionRequestDTO dto) {
        PrevisionResponseDTO response = previsionService.guardar(dto);
        return ResponseEntity.status(201).body(response);
    }

    //-----------------ACTUALIZACION PREVISION----------
    @PutMapping("/{id}")
    public ResponseEntity<PrevisionResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody PrevisionRequestDTO dto){
        return previsionService.actualizar(id,dto).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //-----------------ELIMINAR PREVISION----------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        if (previsionService.obtenerPorId(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        previsionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
