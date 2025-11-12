/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoP3.webservice.dto;
import lombok.*;
import java.time.LocalDate;

/**
 *
 * @author Kenneth
 */
@Data @NoArgsConstructor @AllArgsConstructor
public class ReservaDTO {
    private Long id;
    private Long pacienteId;
    private Long medicoId;
    private Long servicioId;
    private Long consultorioId;
    private LocalDate fecha;
    private String horaInicio;
    private String horaFin;
    private String estado;
    private String codigo;
    private String pacienteNombre;
    private String medicoNombre;
    private String servicioNombre;
    private String consultorioNombre;
}
