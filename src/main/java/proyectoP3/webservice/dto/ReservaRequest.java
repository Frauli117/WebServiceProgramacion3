/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoP3.webservice.dto;
import lombok.Data;
import java.time.LocalDate;
/**
 *
 * @author Kenneth
 */
@Data
public class ReservaRequest {
    private Long pacienteId;
    private Long medicoId;
    private Long servicioId;
    private Long consultorioId;
    private LocalDate fecha;
    private String horaInicio;
}
