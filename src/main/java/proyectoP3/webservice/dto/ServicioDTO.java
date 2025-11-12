/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoP3.webservice.dto;
import lombok.*;
import java.math.BigDecimal;
/**
 *
 * @author Kenneth
 */
@Data @NoArgsConstructor @AllArgsConstructor
public class ServicioDTO {
    private Long id;
    private String nombre;
    private Integer duracionMin;
    private BigDecimal precio;
}
