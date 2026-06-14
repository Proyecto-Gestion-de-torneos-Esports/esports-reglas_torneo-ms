package Esports.Reglas_Torneo.assemblers;

import Esports.Reglas_Torneo.controller.ReglaController;
import Esports.Reglas_Torneo.dto.ReglaResponseDTO;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ReglaModelAssemblers implements RepresentationModelAssembler<ReglaResponseDTO, ReglaResponseDTO> {
    @Override
    public ReglaResponseDTO toModel(ReglaResponseDTO regla) {
        regla.add(linkTo(methodOn(ReglaController.class).buscarPorId(regla.getReglasTorneoId())).withSelfRel());

        regla.add(linkTo(methodOn(ReglaController.class).obtenerTodos()).withRel("todas-las-reglas"));

        return regla;
    }
}

