/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoP3.webservice.service;

import proyectoP3.webservice.entity.Consultorio;
import proyectoP3.webservice.repository.ConsultorioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 *
 * @author Kenneth
 */
@Service
public class ConsultorioService {
    private final ConsultorioRepository repo;

    public ConsultorioService(ConsultorioRepository repo) { this.repo = repo; }

    @Transactional(readOnly = true)
    public List<Consultorio> listar(){ return repo.findAll(); }

    @Transactional(readOnly = true)
    public Consultorio porId(Long id){ return repo.findById(id).orElse(null); }

    @Transactional
    public Consultorio crear(Consultorio c){
        if (c.getId()!=null) c.setId(null);
        return repo.save(c);
    }

    @Transactional
    public Consultorio actualizar(Long id, Consultorio c){
        var db = repo.findById(id).orElseThrow();
        db.setNombre(c.getNombre());
        db.setPiso(c.getPiso());
        db.setCapacidad(c.getCapacidad());
        return repo.save(db);
    }

    @Transactional
    public void eliminar(Long id){
        if (!repo.existsById(id)) return;
        repo.deleteById(id);
    }
}
