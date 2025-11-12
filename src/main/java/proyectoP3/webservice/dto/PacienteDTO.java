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
public class PacienteDTO {
    private Long id;
    private String nombre;
    private String cedula;
    private LocalDate fechaNac;
    private String email;
    private String telefono;
}
