/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoP3.webservice.service;
import proyectoP3.webservice.repository.PacienteRepository;
import proyectoP3.webservice.repository.ConsultorioRepository;
import proyectoP3.webservice.repository.ServicioRepository;
import proyectoP3.webservice.repository.HorarioMedicoRepository;
import proyectoP3.webservice.repository.MedicoRepository;
import proyectoP3.webservice.repository.ReservaRepository;
import proyectoP3.webservice.dto.ReservaRequest;
import proyectoP3.webservice.dto.ReservaDTO;
import proyectoP3.webservice.dto.DtoMapper;
import proyectoP3.webservice.entity.Reserva;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
/**
 *
 * @author Kenneth
 */
@Service
public class ReservaService {
    private final ReservaRepository reservaRepo;
    private final PacienteRepository pacienteRepo;
    private final MedicoRepository medicoRepo;
    private final ServicioRepository servicioRepo;
    private final ConsultorioRepository consultorioRepo;
    private final HorarioMedicoRepository horarioRepo;

    public ReservaService(ReservaRepository reservaRepo, PacienteRepository pacienteRepo, MedicoRepository medicoRepo, ServicioRepository servicioRepo, ConsultorioRepository consultorioRepo, HorarioMedicoRepository horarioRepo) {
        this.reservaRepo = reservaRepo;
        this.pacienteRepo = pacienteRepo;
        this.medicoRepo = medicoRepo;
        this.servicioRepo = servicioRepo;
        this.consultorioRepo = consultorioRepo;
        this.horarioRepo = horarioRepo;
    }

    @Transactional(readOnly = true)
    public List<Reserva> listarPorMedicoYFecha(Long medicoId, LocalDate fecha) {
        return reservaRepo.findByMedico_IdAndFechaOrderByHoraInicioAsc(medicoId, java.sql.Date.valueOf(fecha));
    }
    
    @Transactional(readOnly = true)
    public List<ReservaDTO> listarAgendaDelDia(LocalDate fecha) {
        return reservaRepo.findAgendaDelDia(java.sql.Date.valueOf(fecha)).stream().map(DtoMapper::toDto).toList();
    }

    @Transactional
    public Reserva cancelar(Long id){
        var r = reservaRepo.findById(id).orElseThrow();
        r.setEstado("CANCELADA");
        return reservaRepo.save(r);
    }
    
    @Transactional
    public void eliminarSiCancelada(Long id) {
        var r = reservaRepo.findById(id).orElseThrow();
        if (!"CANCELADA".equalsIgnoreCase(r.getEstado())) {
            throw new IllegalStateException("Solo se puede eliminar si está CANCELADA");
        }
        reservaRepo.delete(r);
    }


    @Transactional
    public Reserva crear(ReservaRequest req) {
        var pac = pacienteRepo.findById(req.getPacienteId()).orElseThrow();
        var med = medicoRepo.findById(req.getMedicoId()).orElseThrow();
        var ser = servicioRepo.findById(req.getServicioId()).orElseThrow();
        var con = consultorioRepo.findById(req.getConsultorioId()).orElseThrow();

        LocalDate fecha = req.getFecha();
        int dow = fecha.getDayOfWeek().getValue();

        var horarios = horarioRepo.findByMedico_IdAndDiaSemanaOrderByHoraInicioAsc(med.getId(), dow);
        if (horarios.isEmpty()) throw new IllegalStateException("El médico no atiende ese día.");

        int hi = toMin(req.getHoraInicio());
        int hf = hi + ser.getDuracionMin();

        boolean dentro = horarios.stream().anyMatch(h -> {
            int a = toMin(h.getHoraInicio()), b = toMin(h.getHoraFin());
            return hi >= a && hf <= b;
        });
        if (!dentro) throw new IllegalArgumentException("Hora fuera del horario del médico.");

        var reservas = reservaRepo.findByMedico_IdAndFechaOrderByHoraInicioAsc(med.getId(), java.sql.Date.valueOf(fecha));
        boolean solapa = reservas.stream().anyMatch(r -> !"CANCELADA".equalsIgnoreCase(r.getEstado()) && toMin(r.getHoraInicio()) < hf && hi < toMin(r.getHoraFin())
        );
        if (solapa) throw new IllegalStateException("Ya existe una reserva en ese horario.");

        var r = new Reserva();
        r.setPaciente(pac); r.setMedico(med); r.setServicio(ser); r.setConsultorio(con);
        r.setFecha(java.sql.Date.valueOf(fecha));
        r.setHoraInicio(req.getHoraInicio());
        r.setHoraFin(toHHMM(hf));
        r.setEstado("CONFIRMADA");
        r.setCodigo(("R-" + UUID.randomUUID().toString().replace("-","")).substring(0,16).toUpperCase());

        return reservaRepo.save(r);
    }

    private int toMin(String hhmm){ var p = hhmm.split(":"); return Integer.parseInt(p[0])*60+Integer.parseInt(p[1]); }
    private String toHHMM(int minutes){
        var fmt = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.of(minutes/60, minutes%60).format(fmt);
    }
}
