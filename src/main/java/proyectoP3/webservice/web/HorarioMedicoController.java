/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package proyectoP3.webservice.web;

import proyectoP3.webservice.dto.HorarioMedicoDTO;
import proyectoP3.webservice.dto.DtoMapper;
import proyectoP3.webservice.service.HorarioMedicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
/**
 *
 * @author Kenneth
 */
@RestController
@RequestMapping("/api/horarios")
@CrossOrigin
public class HorarioMedicoController {

    private final HorarioMedicoService service;

    public HorarioMedicoController(HorarioMedicoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        var list = service.listar().stream().map(DtoMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/medico/{medicoId}/dia/{diaSemana}")
    public ResponseEntity<?> porMedicoYDia(@PathVariable Long medicoId, @PathVariable Integer diaSemana) {
        var list = service.porMedicoYDia(medicoId, diaSemana).stream().map(DtoMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody HorarioMedicoDTO dto) {
        var h = service.crear(dto.getMedicoId(), dto.getDiaSemana(), dto.getHoraInicio(), dto.getHoraFin());
        return ResponseEntity.ok(DtoMapper.toDto(h));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

