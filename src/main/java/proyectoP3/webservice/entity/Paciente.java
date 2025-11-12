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
@Entity @Table(name="PACIENTE")
@Data @NoArgsConstructor @AllArgsConstructor
public class Paciente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, length=100) private String nombre;
    @Column(length=30, unique=true) private String cedula;
    @Column(name="FECHA_NAC") private java.sql.Date fechaNac;
    @Column(length=120) private String email;
    @Column(length=30) private String telefono;
}