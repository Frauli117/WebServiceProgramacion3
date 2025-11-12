/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// src/main/java/com/reservas/web/ReservaController.java
package proyectoP3.webservice.web;

import proyectoP3.webservice.dto.ReservaDTO;
import proyectoP3.webservice.dto.ReservaRequest;
import proyectoP3.webservice.dto.DtoMapper;
import proyectoP3.webservice.entity.Reserva;
import proyectoP3.webservice.service.DisponibilidadService;
import proyectoP3.webservice.service.ReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat; 
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
/**
 *
 * @author Kenneth
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ReservaController {

    private final ReservaService reservaService;
    private final DisponibilidadService dispService;

    public ReservaController(ReservaService reservaService, DisponibilidadService dispService) {
        this.reservaService = reservaService;
        this.dispService = dispService;
    }

    @PostMapping("/reservas")
    public ResponseEntity<?> crear(@RequestBody ReservaRequest req) {
        try {
            Reserva r = reservaService.crear(req);
            return ResponseEntity.ok(DtoMapper.toDto(r));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @GetMapping("/reservas")
    public ResponseEntity<?> listar(@RequestParam Long medicoId, @RequestParam String fecha){
        var list = reservaService.listarPorMedicoYFecha(medicoId, LocalDate.parse(fecha)).stream().map(DtoMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PatchMapping("/reservas/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        reservaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/disponibilidad")
    public ResponseEntity<?> disponibilidad(@RequestParam Long medicoId, @RequestParam String fecha, @RequestParam Long servicioId){
        return ResponseEntity.ok(dispService.obtenerSlots(medicoId, LocalDate.parse(fecha), servicioId));
    }
    @GetMapping("/reservas/dia")
    public ResponseEntity<List<ReservaDTO>> agendaDelDia(@RequestParam("fecha")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(reservaService.listarAgendaDelDia(fecha));
    }
    
    @DeleteMapping("/reservas/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        reservaService.eliminarSiCancelada(id);
        return ResponseEntity.noContent().build();
    }


}

