/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoP3.webservice.service;
import proyectoP3.webservice.entity.Servicio;
import proyectoP3.webservice.repository.ServicioRepository;
import proyectoP3.webservice.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 *
 * @author Kenneth
 */
@Service
public class ServicioService {
    private final ServicioRepository repo;
    private final ReservaRepository reservaRepo;

    public ServicioService(ServicioRepository repo, ReservaRepository reservaRepo) {
        this.repo = repo;
        this.reservaRepo = reservaRepo;
    }

    @Transactional(readOnly = true)
    public List<Servicio> listar(){ return repo.findAll(); }

    @Transactional(readOnly = true)
    public Servicio porId(Long id){ return repo.findById(id).orElse(null); }

    @Transactional
    public Servicio crear(Servicio s){
        if (s.getId()!=null) s.setId(null);
        return repo.save(s);
    }

    @Transactional
    public Servicio actualizar(Long id, Servicio s){
        var db = repo.findById(id).orElseThrow();
        db.setNombre(s.getNombre());
        db.setDuracionMin(s.getDuracionMin());
        db.setPrecio(s.getPrecio());
        return repo.save(db);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repo.existsById(id)) return;

        long reservas = reservaRepo.countByServicio_Id(id);
        if (reservas > 0) {
            throw new IllegalStateException(
                "No se puede eliminar el servicio: tiene " + reservas + " reserva(s) asociada(s)."
            );
        }

        repo.deleteById(id);
    }
}
