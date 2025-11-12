/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoP3.webservice.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 *
 * @author Kenneth
 */
@Entity @Table(name="SERVICIO")
@Data @NoArgsConstructor @AllArgsConstructor
public class Servicio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, length=100) private String nombre;
    @Column(name="DURACION_MIN", nullable=false) private Integer duracionMin;
    @Column(nullable=false, precision=10, scale=2) private java.math.BigDecimal precio;
}
