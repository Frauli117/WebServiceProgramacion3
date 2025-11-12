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
@Entity @Table(name="CONSULTORIO")
@Data @NoArgsConstructor @AllArgsConstructor
public class Consultorio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, length=60) private String nombre;
    @Column(length=10) private String piso;
    @Column private Integer capacidad;
}
