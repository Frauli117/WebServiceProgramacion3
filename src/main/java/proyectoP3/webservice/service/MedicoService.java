/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoP3.webservice.service;
import proyectoP3.webservice.entity.Medico;
import proyectoP3.webservice.repository.MedicoRepository;
import proyectoP3.webservice.repository.HorarioMedicoRepository;
import proyectoP3.webservice.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 *
 * @author Kenneth
 */
@Service
public class MedicoService {

    private final MedicoRepository repo;
    private final HorarioMedicoRepository horarioRepo;
    private final ReservaRepository reservaRepo;

    public MedicoService(MedicoRepository repo, HorarioMedicoRepository horarioRepo, ReservaRepository reservaRepo) {
        this.repo = repo;
        this.horarioRepo = horarioRepo;
        this.reservaRepo = reservaRepo;
    }

    @Transactional(readOnly = true)
    public List<Medico> listar() { return repo.findAll(); }

    @Transactional(readOnly = true)
    public Medico porId(Long id) { return repo.findById(id).orElse(null); }

    @Transactional
    public Medico crear(Medico m) {
        if (m.getId()!=null) m.setId(null);
        if (m.getColegiado()!=null && repo.existsByColegiado(m.getColegiado()))
            throw new IllegalArgumentException("Colegiado ya existe");
        return repo.save(m);
    }

    @Transactional
    public Medico actualizar(Long id, Medico m) {
        var db = repo.findById(id).orElseThrow();
        db.setNombre(m.getNombre());
        db.setEspecialidad(m.getEspecialidad());
        db.setColegiado(m.getColegiado());
        db.setEmail(m.getEmail());
        db.setTelefono(m.getTelefono());
        return repo.save(db);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repo.existsById(id)) return;

        long reservas = reservaRepo.countByMedico_Id(id);
        if (reservas > 0) {
            throw new IllegalStateException(
                "No se puede eliminar el médico: tiene " + reservas + " reserva(s) asociada(s)."
            );
        }

        horarioRepo.deleteByMedico_Id(id);

        repo.deleteById(id);
    }
}
