/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoP3.webservice.web;

import proyectoP3.webservice.dto.PacienteDTO;
import proyectoP3.webservice.dto.DtoMapper;
import proyectoP3.webservice.entity.Paciente;
import proyectoP3.webservice.service.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.stream.Collectors;
/**
 *
 * @author Kenneth
 */
@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin
public class PacienteController {

    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        var list = service.listar().stream().map(DtoMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> porId(@PathVariable Long id) {
        var p = service.porId(id);
        return (p == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(DtoMapper.toDto(p));
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody PacienteDTO dto) {
        try {
            Paciente creado = service.crear(DtoMapper.fromDto(dto));
            return ResponseEntity.created(URI.create("/api/pacientes/" + creado.getId())).body(DtoMapper.toDto(creado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody PacienteDTO dto) {
        try {
            Paciente actualizado = service.actualizar(id, DtoMapper.fromDto(dto));
            return ResponseEntity.ok(DtoMapper.toDto(actualizado));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

}
