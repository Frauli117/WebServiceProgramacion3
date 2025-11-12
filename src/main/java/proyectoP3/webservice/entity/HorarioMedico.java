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
@Entity @Table(name="HORARIO_MEDICO")
@Data @NoArgsConstructor @AllArgsConstructor
public class HorarioMedico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="MEDICO_ID", nullable=false)
    private Medico medico;

    @Column(name="DIA_SEMANA", nullable=false) private Integer diaSemana;
    @Column(name="HORA_INICIO", nullable=false, length=5) private String horaInicio;
    @Column(name="HORA_FIN",    nullable=false, length=5) private String horaFin;
}
