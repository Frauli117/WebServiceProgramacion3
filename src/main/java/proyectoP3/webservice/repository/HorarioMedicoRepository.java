/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoP3.webservice.repository;

import proyectoP3.webservice.entity.HorarioMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
/**
 *
 * @author Kenneth
 */

public interface HorarioMedicoRepository extends JpaRepository<HorarioMedico, Long> {
    List<HorarioMedico> findByMedico_IdAndDiaSemanaOrderByHoraInicioAsc(Long medicoId, Integer diaSemana);
    void deleteByMedico_Id(Long medicoId);
}