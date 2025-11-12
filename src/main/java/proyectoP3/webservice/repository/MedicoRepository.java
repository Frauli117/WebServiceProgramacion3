/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoP3.webservice.repository;

import proyectoP3.webservice.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author Kenneth
 */

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    boolean existsByColegiado(String colegiado);
}
