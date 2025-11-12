/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoP3.webservice.dto;
import lombok.*;
/**
 *
 * @author Kenneth
 */
@Data @NoArgsConstructor @AllArgsConstructor
public class MedicoDTO {
    private Long id;
    private String nombre;
    private String especialidad;
    private String colegiado;
    private String email;
    private String telefono;
}
