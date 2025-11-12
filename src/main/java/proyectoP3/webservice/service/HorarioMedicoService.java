/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoP3.webservice.service;
import proyectoP3.webservice.entity.HorarioMedico;
import proyectoP3.webservice.entity.Medico;
import proyectoP3.webservice.repository.HorarioMedicoRepository;
import proyectoP3.webservice.repository.MedicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
/**
 *
 * @author Kenneth
 */
@Service
public class HorarioMedicoService {
    private final HorarioMedicoRepository repo;
    private final MedicoRepository medicoRepo;

    public HorarioMedicoService(HorarioMedicoRepository repo, MedicoRepository medicoRepo) {
        this.repo = repo; this.medicoRepo = medicoRepo;
    }

    @Transactional(readOnly = true)
    public List<HorarioMedico> listar(){ return repo.findAll(); }

    @Transactional(readOnly = true)
    public List<HorarioMedico> porMedicoYDia(Long medicoId, Integer diaSemana){
        return repo.findByMedico_IdAndDiaSemanaOrderByHoraInicioAsc(medicoId, diaSemana);
    }

    @Transactional
    public HorarioMedico crear(Long medicoId, Integer diaSemana, String horaInicio, String horaFin){
        if (diaSemana == null || diaSemana < 1 || diaSemana > 7)
            throw new IllegalArgumentException("diaSemana debe estar entre 1..7");
            var m = medicoRepo.findById(medicoId).orElseThrow();

            LocalTime hi = LocalTime.parse(horaInicio);
            LocalTime hf = LocalTime.parse(horaFin);
        if (!hi.isBefore(hf)) throw new IllegalArgumentException("horaInicio < horaFin");
        var h = new HorarioMedico();
        h.setMedico(m);
        h.setDiaSemana(diaSemana);
        h.setHoraInicio(horaInicio);
        h.setHoraFin(horaFin);
        return repo.save(h);
    }

    @Transactional
    public void eliminar(Long id){
        if (!repo.existsById(id)) return;
        repo.deleteById(id);
    }
}
