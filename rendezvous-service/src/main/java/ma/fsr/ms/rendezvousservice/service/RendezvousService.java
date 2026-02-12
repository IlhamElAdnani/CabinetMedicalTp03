package ma.fsr.ms.rendezvousservice.service;

import java.time.LocalDateTime;
import java.util.List;
import ma.fsr.ms.rendezvousservice.entity.Rendezvous;
import ma.fsr.ms.rendezvousservice.entity.RendezvousStatut;
import ma.fsr.ms.rendezvousservice.exception.BusinessException;
import ma.fsr.ms.rendezvousservice.exception.RendezvousNotFoundException;
import ma.fsr.ms.rendezvousservice.repository.RendezvousRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class RendezvousService {

    private static final String PATIENT_URL = "http://localhost:8082/internal/api/v1/patients/{id}";
    private static final String MEDECIN_URL = "http://localhost:8083/internal/api/v1/medecins/{id}";

    private final RendezvousRepository repository;
    private final RestTemplate restTemplate;

    public RendezvousService(RendezvousRepository repository) {
        this.repository = repository;
        this.restTemplate = new RestTemplate();
    }

    public List<Rendezvous> getAll() {
        return repository.findAll();
    }

    public Rendezvous getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RendezvousNotFoundException(id));
    }

    public List<Rendezvous> getByPatientId(Long patientId) {
        return repository.findByPatientId(patientId);
    }

    public List<Rendezvous> getByMedecinId(Long medecinId) {
        return repository.findByMedecinId(medecinId);
    }

    public Rendezvous create(Rendezvous rendezvous) {
        validateDateFuture(rendezvous.getDateRendezvous());
        ensurePatientExists(rendezvous.getPatientId());
        ensureMedecinExists(rendezvous.getMedecinId());

        rendezvous.setId(null);
        if (rendezvous.getStatut() == null) {
            rendezvous.setStatut(RendezvousStatut.PLANIFIE);
        } else {
            validateStatut(rendezvous.getStatut());
        }
        return repository.save(rendezvous);
    }

    public Rendezvous update(Long id, Rendezvous rendezvous) {
        Rendezvous existing = repository.findById(id).orElseThrow(() -> new RendezvousNotFoundException(id));
        validateDateFuture(rendezvous.getDateRendezvous());
        ensurePatientExists(rendezvous.getPatientId());
        ensureMedecinExists(rendezvous.getMedecinId());

        existing.setDateRendezvous(rendezvous.getDateRendezvous());
        existing.setPatientId(rendezvous.getPatientId());
        existing.setMedecinId(rendezvous.getMedecinId());
        if (rendezvous.getStatut() == null) {
            if (existing.getStatut() == null) {
                existing.setStatut(RendezvousStatut.PLANIFIE);
            }
        } else {
            validateStatut(rendezvous.getStatut());
            existing.setStatut(rendezvous.getStatut());
        }
        return repository.save(existing);
    }

    public Rendezvous updateStatut(Long id, String statut) {
        Rendezvous existing = repository.findById(id).orElseThrow(() -> new RendezvousNotFoundException(id));
        RendezvousStatut parsed = parseStatut(statut);
        existing.setStatut(parsed);
        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RendezvousNotFoundException(id);
        }
        repository.deleteById(id);
    }

    private void validateDateFuture(LocalDateTime date) {
        if (date == null || !date.isAfter(LocalDateTime.now())) {
            throw new BusinessException("La date du rendez-vous doit être future.");
        }
    }

    private void validateStatut(RendezvousStatut statut) {
        if (statut == null) {
            throw new BusinessException("Statut invalide. Valeurs possibles : PLANIFIE, ANNULE, TERMINE.");
        }
    }

    private RendezvousStatut parseStatut(String statut) {
        if (statut == null) {
            throw new BusinessException("Statut invalide. Valeurs possibles : PLANIFIE, ANNULE, TERMINE.");
        }
        try {
            return RendezvousStatut.valueOf(statut);
        } catch (IllegalArgumentException ex) {
            throw new BusinessException("Statut invalide. Valeurs possibles : PLANIFIE, ANNULE, TERMINE.");
        }
    }

    private void ensurePatientExists(Long patientId) {
        if (patientId == null) {
            throw new BusinessException("Patient introuvable.");
        }
        try {
            restTemplate.getForEntity(PATIENT_URL, Object.class, patientId);
        } catch (HttpClientErrorException ex) {
            HttpStatusCode status = ex.getStatusCode();
            if (status != null && status.value() == 404) {
                throw new BusinessException("Patient introuvable.");
            }
        }
    }

    private void ensureMedecinExists(Long medecinId) {
        if (medecinId == null) {
            throw new BusinessException("Médecin introuvable.");
        }
        try {
            restTemplate.getForEntity(MEDECIN_URL, Object.class, medecinId);
        } catch (HttpClientErrorException ex) {
            HttpStatusCode status = ex.getStatusCode();
            if (status != null && status.value() == 404) {
                throw new BusinessException("Médecin introuvable.");
            }
        }
    }
}
