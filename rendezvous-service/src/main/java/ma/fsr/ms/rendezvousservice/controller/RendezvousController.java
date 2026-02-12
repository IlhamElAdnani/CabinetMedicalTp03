package ma.fsr.ms.rendezvousservice.controller;

import jakarta.validation.Valid;
import java.util.List;
import ma.fsr.ms.rendezvousservice.entity.Rendezvous;
import ma.fsr.ms.rendezvousservice.service.RendezvousService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/api/v1/rendezvous")
public class RendezvousController {

    private final RendezvousService service;

    public RendezvousController(RendezvousService service) {
        this.service = service;
    }

    @GetMapping
    public List<Rendezvous> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Rendezvous getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/patient/{id}")
    public List<Rendezvous> getByPatient(@PathVariable Long id) {
        return service.getByPatientId(id);
    }

    @GetMapping("/medecin/{id}")
    public List<Rendezvous> getByMedecin(@PathVariable Long id) {
        return service.getByMedecinId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Rendezvous create(@Valid @RequestBody Rendezvous rendezvous) {
        return service.create(rendezvous);
    }

    @PutMapping("/{id}")
    public Rendezvous update(@PathVariable Long id, @Valid @RequestBody Rendezvous rendezvous) {
        return service.update(id, rendezvous);
    }

    @PatchMapping("/{id}/statut")
    public Rendezvous updateStatut(@PathVariable Long id, @Valid @RequestBody StatutUpdateRequest request) {
        return service.updateStatut(id, request == null ? null : request.getStatut());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
