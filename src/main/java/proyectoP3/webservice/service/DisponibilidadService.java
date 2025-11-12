/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoP3.webservice.service;
import proyectoP3.webservice.repository.HorarioMedicoRepository;
import proyectoP3.webservice.repository.ReservaRepository;
import proyectoP3.webservice.repository.ServicioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
/**
 *
 * @author Kenneth
 */
@Service
public class DisponibilidadService {

    private final HorarioMedicoRepository horarioRepo;
    private final ReservaRepository reservaRepo;
    private final ServicioRepository servicioRepo;

    public DisponibilidadService(HorarioMedicoRepository horarioRepo, ReservaRepository reservaRepo, ServicioRepository servicioRepo) {
        this.horarioRepo = horarioRepo;
        this.reservaRepo = reservaRepo;
        this.servicioRepo = servicioRepo;
    }

    public List<String> obtenerSlots(Long medicoId, LocalDate fecha, Long servicioId) {
        int dow = fecha.getDayOfWeek().getValue();
        var horarios = horarioRepo.findByMedico_IdAndDiaSemanaOrderByHoraInicioAsc(medicoId, dow);
        if (horarios.isEmpty()) return List.of();

        int durMin = servicioRepo.findById(servicioId).orElseThrow().getDuracionMin();
        var reservas = reservaRepo.findByMedico_IdAndFechaOrderByHoraInicioAsc(medicoId, java.sql.Date.valueOf(fecha));

        List<int[]> ocupados = reservas.stream().filter(r -> !"CANCELADA".equalsIgnoreCase(r.getEstado())).map(r -> new int[]{ toMin(r.getHoraInicio()), toMin(r.getHoraFin()) }).collect(Collectors.toList());

        var fmt = DateTimeFormatter.ofPattern("HH:mm");
        LinkedHashSet<String> slots = new LinkedHashSet<>();
        for (var h : horarios) {
            int inicio = toMin(h.getHoraInicio());
            int fin    = toMin(h.getHoraFin());

            for (int t = inicio; t + durMin <= fin; t += durMin) {
                final int tStart = t;
                final int tEnd   = tStart + durMin;

                boolean solapa = ocupados.stream().anyMatch(o -> o[0] < tEnd && tStart < o[1]);

                if (!solapa) {
                    slots.add(LocalTime.of(tStart / 60, tStart % 60).format(fmt));
                }
            }
        }

        return new ArrayList<>(slots);
    }

    private int toMin(String hhmm){ var p = hhmm.split(":"); return Integer.parseInt(p[0])*60+Integer.parseInt(p[1]); }
}
