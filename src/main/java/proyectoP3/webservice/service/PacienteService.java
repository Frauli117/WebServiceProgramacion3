/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoP3.webservice.service;
import proyectoP3.webservice.entity.Paciente;
import proyectoP3.webservice.repository.PacienteRepository;
import proyectoP3.webservice.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
/**
 *
 * @author Kenneth
 */
@Service
public class PacienteService {
    private final PacienteRepository repo;
    private final ReservaRepository reservaRepo;

    public PacienteService(PacienteRepository repo, ReservaRepository reservaRepo) {
        this.repo = repo;
        this.reservaRepo = reservaRepo;
    }

    @Transactional(readOnly = true)
    public List<Paciente> listar(){ return repo.findAll(); }

    @Transactional(readOnly = true)
    public Paciente porId(Long id){ return repo.findById(id).orElse(null); }

    @Transactional
    public Paciente crear(Paciente p){
        if (p.getId()!=null) p.setId(null);
        if (p.getCedula()!=null && repo.existsByCedula(p.getCedula()))
            throw new IllegalArgumentException("Cédula ya existe");
        return repo.save(p);
    }

    @Transactional
    public Paciente actualizar(Long id, Paciente p){
        var db = repo.findById(id).orElseThrow();
        db.setNombre(p.getNombre());
        db.setCedula(p.getCedula());
        db.setFechaNac(p.getFechaNac());
        db.setEmail(p.getEmail());
        db.setTelefono(p.getTelefono());
        return repo.save(db);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repo.existsById(id)) return;

        long reservas = reservaRepo.countByPaciente_Id(id);
        if (reservas > 0) {
            throw new IllegalStateException(
                "No se puede eliminar el paciente: tiene " + reservas + " reserva(s) asociada(s)."
            );
        }

        repo.deleteById(id);
    }
}
