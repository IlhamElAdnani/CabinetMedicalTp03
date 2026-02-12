package ma.fsr.ms.medecinservice.controller;

import jakarta.validation.Valid;
import java.util.List;
import ma.fsr.ms.medecinservice.entity.Medecin;
import ma.fsr.ms.medecinservice.service.MedecinService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/api/v1/medecins")
public class MedecinController {

    private final MedecinService service;

    public MedecinController(MedecinService service) {
        this.service = service;
    }

    @GetMapping
    public List<Medecin> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Medecin getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Medecin create(@Valid @RequestBody Medecin medecin) {
        return service.create(medecin);
    }

    @PutMapping("/{id}")
    public Medecin update(@PathVariable Long id, @Valid @RequestBody Medecin medecin) {
        return service.update(id, medecin);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
