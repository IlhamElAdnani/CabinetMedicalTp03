package ma.fsr.ms.consultationservice.controller;

import jakarta.validation.Valid;
import java.util.List;
import ma.fsr.ms.consultationservice.entity.Consultation;
import ma.fsr.ms.consultationservice.service.ConsultationService;
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
@RequestMapping("/internal/api/v1/consultations")
public class ConsultationController {

    private final ConsultationService service;

    public ConsultationController(ConsultationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Consultation> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Consultation getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/rendezvous/{id}")
    public List<Consultation> getByRendezvous(@PathVariable Long id) {
        return service.getByRendezvousId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Consultation create(@Valid @RequestBody Consultation consultation) {
        return service.create(consultation);
    }

    @PutMapping("/{id}")
    public Consultation update(@PathVariable Long id, @Valid @RequestBody Consultation consultation) {
        return service.update(id, consultation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
