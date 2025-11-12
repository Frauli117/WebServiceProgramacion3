/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package proyectoP3.webservice.web;

import proyectoP3.webservice.dto.ConsultorioDTO;
import proyectoP3.webservice.dto.DtoMapper;
import proyectoP3.webservice.entity.Consultorio;
import proyectoP3.webservice.service.ConsultorioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.stream.Collectors;
/**
 *
 * @author Kenneth
 */
@RestController
@RequestMapping("/api/consultorios")
@CrossOrigin
public class ConsultorioController {

    private final ConsultorioService service;

    public ConsultorioController(ConsultorioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        var list = service.listar().stream().map(DtoMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> porId(@PathVariable Long id) {
        var c = service.porId(id);
        return (c == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(DtoMapper.toDto(c));
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody ConsultorioDTO dto) {
        Consultorio creado = service.crear(DtoMapper.fromDto(dto));
        return ResponseEntity.created(URI.create("/api/consultorios/" + creado.getId())).body(DtoMapper.toDto(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ConsultorioDTO dto) {
        try {
            Consultorio actualizado = service.actualizar(id, DtoMapper.fromDto(dto));
            return ResponseEntity.ok(DtoMapper.toDto(actualizado));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

