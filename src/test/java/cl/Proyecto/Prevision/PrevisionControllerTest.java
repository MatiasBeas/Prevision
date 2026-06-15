package cl.Proyecto.Prevision;

import cl.Proyecto.Prevision.Controller.PrevisionController;
import cl.Proyecto.Prevision.Service.PrevisionService;
import cl.Proyecto.Prevision.assemblers.PrevisionAsembler;
import cl.Proyecto.Prevision.dto.PrevisionRequestDTO;
import cl.Proyecto.Prevision.dto.PrevisionResponseDTO;
import cl.Proyecto.Prevision.security.JwtAuthFilter;
import cl.Proyecto.Prevision.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PrevisionController.class)
@Import(PrevisionAsembler.class)
@DisplayName("Tests unitarios - PrevisionController")
class PrevisionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PrevisionService previsionService;

    @MockitoBean
    private PrevisionAsembler previsionAsembler;

    // Hay que mockear estos dos porque @WebMvcTest carga Spring Security
    // y JwtAuthFilter necesita JwtService para funcionar
    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private JwtAuthFilter jwtAuthFilter;

    @Test
    @WithMockUser
    @DisplayName("GIVEN: previsiones en BD WHEN: GET /previsiones THEN: 200 con lista")
    void shouldReturn200_whenGetAll() throws Exception {
        PrevisionResponseDTO dto = new PrevisionResponseDTO(1L, "Fonasa");
        when(previsionService.obtenerTodos()).thenReturn(List.of(dto));
        when(previsionAsembler.toModel(any())).thenReturn(EntityModel.of(dto));

        mockMvc.perform(get("/previsiones"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("GIVEN: ID existente WHEN: GET /previsiones/{id} THEN: 200 con datos")
    void shouldReturn200_whenGetById() throws Exception {
        PrevisionResponseDTO dto = new PrevisionResponseDTO(1L, "Fonasa");
        when(previsionService.obtenerPorId(1L)).thenReturn(Optional.of(dto));
        when(previsionAsembler.toModel(any())).thenReturn(EntityModel.of(dto));

        mockMvc.perform(get("/previsiones/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("GIVEN: ID inexistente WHEN: GET /previsiones/{id} THEN: 404")
    void shouldReturn404_whenGetByIdNotFound() throws Exception {
        when(previsionService.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/previsiones/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    @DisplayName("GIVEN: DTO valido WHEN: POST /previsiones THEN: 201")
    void shouldReturn201_whenCreate() throws Exception {
        PrevisionRequestDTO request = new PrevisionRequestDTO("Fonasa");
        PrevisionResponseDTO response = new PrevisionResponseDTO(1L, "Fonasa");
        when(previsionService.guardar(any())).thenReturn(response);
        when(previsionAsembler.toModel(any())).thenReturn(EntityModel.of(response));

        mockMvc.perform(post("/previsiones")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    @DisplayName("GIVEN: nombre vacio WHEN: POST /previsiones THEN: 400")
    void shouldReturn400_whenNameIsBlank() throws Exception {
        PrevisionRequestDTO request = new PrevisionRequestDTO("");

        mockMvc.perform(post("/previsiones")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    @DisplayName("GIVEN: ID existente WHEN: PUT /previsiones/{id} THEN: 200")
    void shouldReturn200_whenUpdate() throws Exception {
        PrevisionRequestDTO request = new PrevisionRequestDTO("Fonasa Actualizado");
        PrevisionResponseDTO response = new PrevisionResponseDTO(1L, "Fonasa Actualizado");
        when(previsionService.actualizar(eq(1L), any())).thenReturn(Optional.of(response));
        when(previsionAsembler.toModel(any())).thenReturn(EntityModel.of(response));

        mockMvc.perform(put("/previsiones/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("GIVEN: ID existente WHEN: DELETE /previsiones/{id} THEN: 204")
    void shouldReturn204_whenDelete() throws Exception {
        PrevisionResponseDTO dto = new PrevisionResponseDTO(1L, "Fonasa");
        when(previsionService.obtenerPorId(1L)).thenReturn(Optional.of(dto));

        mockMvc.perform(delete("/previsiones/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    @DisplayName("GIVEN: ID inexistente WHEN: DELETE /previsiones/{id} THEN: 404")
    void shouldReturn404_whenDeleteNotFound() throws Exception {
        when(previsionService.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/previsiones/99")
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }
}