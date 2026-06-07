package cl.Proyecto.Prevision.assemblers;

import cl.Proyecto.Prevision.Controller.PrevisionController;
import cl.Proyecto.Prevision.dto.PrevisionResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PrevisionAsembler implements RepresentationModelAssembler <PrevisionResponseDTO, EntityModel<PrevisionResponseDTO>> {

    @Override
    public EntityModel<PrevisionResponseDTO> toModel(PrevisionResponseDTO prevision){
        return EntityModel.of(prevision,
                linkTo(methodOn(PrevisionController.class).obtenerPorId(prevision.getIdPrevision())).withSelfRel(),
                linkTo(methodOn(PrevisionController.class).obtenerTodos()).withRel("todas-las-previsiones"));

    }
}
