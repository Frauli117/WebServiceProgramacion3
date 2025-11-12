/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoP3.webservice.repository;
import proyectoP3.webservice.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
/**
 *
 * @author Kenneth
 */
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @EntityGraph(attributePaths = {"paciente","medico","servicio","consultorio"})
    List<Reserva> findByMedico_IdAndFechaOrderByHoraInicioAsc(Long medicoId, java.sql.Date fecha);

    @EntityGraph(attributePaths = {"paciente","medico","servicio","consultorio"})
    List<Reserva> findByConsultorio_IdAndFechaOrderByHoraInicioAsc(Long consultorioId, java.sql.Date fecha);
    
    @Query("""
        select r from Reserva r
        join fetch r.paciente
        join fetch r.medico
        join fetch r.servicio
        join fetch r.consultorio
        where r.fecha = :fecha
        order by r.medico.nombre asc, r.horaInicio asc
     """)
    List<Reserva> findAgendaDelDia(@Param("fecha") java.sql.Date fecha);
    
    long countByMedico_Id(Long medicoId);
    long countByPaciente_Id(Long pacienteId);
    long countByServicio_Id(Long servicioId);
}
