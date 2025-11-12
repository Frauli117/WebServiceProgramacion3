/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoP3.webservice.repository;
import proyectoP3.webservice.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author Kenneth
 */
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    boolean existsByCedula(String cedula);
}
