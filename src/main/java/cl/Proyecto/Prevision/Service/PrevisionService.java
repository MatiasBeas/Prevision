package cl.Proyecto.Prevision.Service;

import cl.Proyecto.Prevision.dto.PrevisionRequestDTO;
import cl.Proyecto.Prevision.dto.PrevisionResponseDTO;
import cl.Proyecto.Prevision.modelo.Prevision;
import cl.Proyecto.Prevision.repository.PrevisionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return previsionRepository.findAll().stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<PrevisionResponseDTO> obtenerPorId(Long id){
        return previsionRepository.findById(id).map(this::mapToDTO);
    }

    public PrevisionResponseDTO guardar(PrevisionRequestDTO dto){
        Prevision prevision = new Prevision(
                null,
                dto.getNombre()
        );
        return  mapToDTO(previsionRepository.save(prevision));
    }

    public Optional<PrevisionResponseDTO> actualizar(Long id, PrevisionRequestDTO dto){
        return previsionRepository.findById(id)
                .map(existente -> {existente.setNombre(dto.getNombre());
                return mapToDTO(previsionRepository.save(existente));
                });
    }

    public void eliminar(Long id){
        previsionRepository.deleteById(id);
    }
}
