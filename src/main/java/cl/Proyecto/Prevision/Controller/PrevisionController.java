package cl.Proyecto.Prevision.Controller;

import cl.Proyecto.Prevision.Service.PrevisionService;
import cl.Proyecto.Prevision.assemblers.PrevisionAsembler;
import cl.Proyecto.Prevision.dto.PrevisionRequestDTO;
import cl.Proyecto.Prevision.dto.PrevisionResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/previsiones")
@RequiredArgsConstructor
@Tag(name = "Gestion de Previsiones", description = "Endpoints para administrar el sistema de Previsiones de el Hospital")
public class PrevisionController {

    private final PrevisionService previsionService;
    private final PrevisionAsembler previsionAsembler;

    //-----------------BUSCAR TODAS LAS PREVISIONES----------
    @Operation(summary = "Obtener todas las previsiones", description = "Retorna una lista completa de todas las Previsiones ingresadas.")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PrevisionResponseDTO>>> obtenerTodos(){
        List<EntityModel<PrevisionResponseDTO>> prevision = previsionService.obtenerTodos().stream()
                .map(previsionAsembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(prevision,
                linkTo(methodOn(PrevisionController.class).obtenerTodos()).withSelfRel()));
    }

    //-----------------BUSCAR POR ID DE PREVISION----------
    @Operation(summary = "Obtener las previsiones por ID", description = "Retorna la prevision con el ID indicado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prevision encontrada exitosamente"),
            @ApiResponse(responseCode = "404", description = "La prevision ingresada no existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PrevisionResponseDTO>> obtenerPorId(
            @Parameter(description = "Identificador de la prevision a consultar", example = "1")
            @PathVariable Long id) {
        return previsionService.obtenerPorId(id)
                .map(previsionAsembler::toModel) // <-- Delegamos la creación de links al Assembler
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //-----------------GUARDAR PREVISION----------
    @Operation(summary = "Creacion de previsiones", description = "Se guardara la prevision con un ID autoincremental y los datos que se ingresen.")
    @ApiResponse(responseCode = "201", description = "Prevision creada exitosamente")
    @PostMapping
    public ResponseEntity<EntityModel<PrevisionResponseDTO>> crearPrevision(@Valid @RequestBody PrevisionRequestDTO dto) {
        PrevisionResponseDTO prevision = previsionService.guardar(dto);
        return ResponseEntity.status(201).body(previsionAsembler.toModel(prevision));
    }

    //-----------------ACTUALIZACION PREVISION----------
    @Operation(summary = "Actualizar la prevision.", description = "Se actualizara la prevision segun el ID indicado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prevision actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "La prevision indicada no existe")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<PrevisionResponseDTO>> actualizar(
            @Parameter(description = "ID de la Prevision que se desea actualizar", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody PrevisionRequestDTO dto) {
        return previsionService.actualizar(id, dto)
                .map(previsionAsembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    //-----------------ELIMINAR PREVISION----------
    @Operation(summary = "Eliminacion de previsiones.", description = "Se eliminara la prevision segun el ID que se desea.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Prevision eliminada con éxito (No Content)"),
            @ApiResponse(responseCode = "404", description = "La Prevision no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPrevision(
            @Parameter(description = "ID de la Prevision que se desea eliminar", example = "1")
            @PathVariable Long id) {
        if (previsionService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        previsionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
