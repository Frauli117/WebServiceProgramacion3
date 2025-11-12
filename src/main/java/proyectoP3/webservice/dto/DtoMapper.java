    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoP3.webservice.dto;

import proyectoP3.webservice.entity.Paciente;
import proyectoP3.webservice.entity.Medico;
import proyectoP3.webservice.entity.Reserva;
import proyectoP3.webservice.entity.HorarioMedico;
import proyectoP3.webservice.entity.Servicio;
import proyectoP3.webservice.entity.Consultorio;
/**
 *
 * @author Kenneth
 */
public class DtoMapper {

    public static MedicoDTO toDto(Medico m){
        if (m == null) return null;
        return new MedicoDTO(m.getId(), m.getNombre(), m.getEspecialidad(),m.getColegiado(), m.getEmail(), m.getTelefono());
    }

    public static PacienteDTO toDto(Paciente p){
        if (p == null) return null;
        var fn = (p.getFechaNac() == null) ? null : p.getFechaNac().toLocalDate();
        return new PacienteDTO(p.getId(), p.getNombre(), p.getCedula(),fn, p.getEmail(), p.getTelefono());
    }

    public static ServicioDTO toDto(Servicio s){
        if (s == null) return null;
        return new ServicioDTO(s.getId(), s.getNombre(), s.getDuracionMin(), s.getPrecio());
    }

    public static ConsultorioDTO toDto(Consultorio c){
        if (c == null) return null;
        return new ConsultorioDTO(c.getId(), c.getNombre(), c.getPiso(), c.getCapacidad());
    }

    public static HorarioMedicoDTO toDto(HorarioMedico h){
        if (h == null) return null;
        return new HorarioMedicoDTO(h.getId(), h.getMedico().getId(), h.getDiaSemana(),h.getHoraInicio(), h.getHoraFin());
    }

    public static ReservaDTO toDto(Reserva r){
        if (r == null) return null;
        var dto = new ReservaDTO();
        dto.setId(r.getId());
        dto.setPacienteId(r.getPaciente().getId());
        dto.setMedicoId(r.getMedico().getId());
        dto.setServicioId(r.getServicio().getId());
        dto.setConsultorioId(r.getConsultorio().getId());
        dto.setFecha(r.getFecha().toLocalDate());
        dto.setHoraInicio(r.getHoraInicio());
        dto.setHoraFin(r.getHoraFin());
        dto.setEstado(r.getEstado());
        dto.setCodigo(r.getCodigo());
        dto.setPacienteNombre(r.getPaciente().getNombre());
        dto.setMedicoNombre(r.getMedico().getNombre());
        dto.setServicioNombre(r.getServicio().getNombre());
        dto.setConsultorioNombre(r.getConsultorio().getNombre());
        return dto;
    }

    public static Medico fromDto(MedicoDTO dto){
        if (dto == null) return null;
        var e = new Medico();
        e.setId(dto.getId());
        e.setNombre(dto.getNombre());
        e.setEspecialidad(dto.getEspecialidad());
        e.setColegiado(dto.getColegiado());
        e.setEmail(dto.getEmail());
        e.setTelefono(dto.getTelefono());
        return e;
    }

    public static Paciente fromDto(PacienteDTO dto){
        if (dto == null) return null;
        var e = new Paciente();
        e.setId(dto.getId());
        e.setNombre(dto.getNombre());
        e.setCedula(dto.getCedula());
        e.setFechaNac(dto.getFechaNac() == null ? null : java.sql.Date.valueOf(dto.getFechaNac()));
        e.setEmail(dto.getEmail());
        e.setTelefono(dto.getTelefono());
        return e;
    }

    public static Servicio fromDto(ServicioDTO dto){
        if (dto == null) return null;
        var e = new Servicio();
        e.setId(dto.getId());
        e.setNombre(dto.getNombre());
        e.setDuracionMin(dto.getDuracionMin());
        e.setPrecio(dto.getPrecio());
        return e;
    }

    public static Consultorio fromDto(ConsultorioDTO dto){
        if (dto == null) return null;
        var e = new Consultorio();
        e.setId(dto.getId());
        e.setNombre(dto.getNombre());
        e.setPiso(dto.getPiso());
        e.setCapacidad(dto.getCapacidad());
        return e;
    }

    public static HorarioMedico fromDto(HorarioMedicoDTO dto){
        if (dto == null) return null;
        var h = new HorarioMedico();
        h.setId(dto.getId());
        var m = new Medico();
        m.setId(dto.getMedicoId());
        h.setMedico(m);
        h.setDiaSemana(dto.getDiaSemana());
        h.setHoraInicio(dto.getHoraInicio());
        h.setHoraFin(dto.getHoraFin());
        return h;
    }
}

