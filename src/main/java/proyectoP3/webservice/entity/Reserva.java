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
@Entity @Table(name="RESERVA")
@Data @NoArgsConstructor @AllArgsConstructor
public class Reserva {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="PACIENTE_ID", nullable=false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="MEDICO_ID", nullable=false)
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="SERVICIO_ID", nullable=false)
    private Servicio servicio;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="CONSULTORIO_ID", nullable=false)
    private Consultorio consultorio;

    @Column(nullable=false) private java.sql.Date fecha;
    @Column(name="HORA_INICIO", nullable=false, length=5) private String horaInicio;
    @Column(name="HORA_FIN",    nullable=false, length=5) private String horaFin;
    @Column(length=20) private String estado;
    @Column(length=16, unique=true) private String codigo;
}