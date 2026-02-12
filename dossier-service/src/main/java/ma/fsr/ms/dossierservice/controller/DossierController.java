package ma.fsr.ms.dossierservice.controller;

import ma.fsr.ms.dossierservice.dto.DossierPatientDto;
import ma.fsr.ms.dossierservice.service.DossierService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/api/v1/dossiers")
public class DossierController {

    private final DossierService service;

    public DossierController(DossierService service) {
        this.service = service;
    }

    @GetMapping("/patient/{patientId}")
    public DossierPatientDto getByPatient(@PathVariable Long patientId) {
        return service.getDossierByPatientId(patientId);
    }
}
