package cl.Proyecto.Prevision.Service;

import cl.Proyecto.Prevision.dto.PrevisionRequestDTO;
import cl.Proyecto.Prevision.dto.PrevisionResponseDTO;
import cl.Proyecto.Prevision.modelo.Prevision;
import cl.Proyecto.Prevision.repository.PrevisionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrevisionService {
    private final PrevisionRepository previsionRepository;

    //Mapeo Privado: Entidad -> Response
    private PrevisionResponseDTO mapToDTO(Prevision prevision){
        return new PrevisionResponseDTO(
                prevision.getIdPrevision(),
                prevision.getNombre()
        );
    }

    public List<PrevisionResponseDTO> obtenerTodos(){
        log.info("Consultando TODAS las previsiones");
        return previsionRepository.findAll().stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<PrevisionResponseDTO> obtenerPorId(Long id){
        log.info("Consultando la prevision con el ID: " + id);
        return previsionRepository.findById(id).map(this::mapToDTO);
    }

    public PrevisionResponseDTO guardar(PrevisionRequestDTO dto){
        log.info("Creando Prevision");
        Prevision prevision = new Prevision(
                null,
                dto.getNombre()
        );
        return  mapToDTO(previsionRepository.save(prevision));
    }

    public Optional<PrevisionResponseDTO> actualizar(Long id, PrevisionRequestDTO dto){
        log.info("Actualizando Prevision con ID: " + id);
        return previsionRepository.findById(id)
                .map(existente -> {existente.setNombre(dto.getNombre());
                return mapToDTO(previsionRepository.save(existente));
                });
    }

    public void eliminar(Long id){
        previsionRepository.deleteById(id);
        log.info("Eliminado Prevision con ID: "+ id);
    }
}
