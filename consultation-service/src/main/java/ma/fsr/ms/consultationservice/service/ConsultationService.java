package ma.fsr.ms.consultationservice.service;

import java.time.LocalDateTime;
import java.util.List;
import ma.fsr.ms.consultationservice.entity.Consultation;
import ma.fsr.ms.consultationservice.exception.BusinessException;
import ma.fsr.ms.consultationservice.exception.ConsultationNotFoundException;
import ma.fsr.ms.consultationservice.repository.ConsultationRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ConsultationService {

    private static final String RENDEZVOUS_URL = "http://localhost:8084/internal/api/v1/rendezvous/{id}";

    private final ConsultationRepository repository;
    private final RestTemplate restTemplate;

    public ConsultationService(ConsultationRepository repository) {
        this.repository = repository;
        this.restTemplate = new RestTemplate();
    }

    public List<Consultation> getAll() {
        return repository.findAll();
    }

    public Consultation getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ConsultationNotFoundException(id));
    }

    public List<Consultation> getByRendezvousId(Long rendezvousId) {
        return repository.findByRendezvousId(rendezvousId);
    }

    public Consultation create(Consultation consultation) {
        validateDate(consultation.getDateConsultation());
        ensureRendezvousExists(consultation.getRendezvousId());

        consultation.setId(null);
        return repository.save(consultation);
    }

    public Consultation update(Long id, Consultation consultation) {
        Consultation existing = repository.findById(id).orElseThrow(() -> new ConsultationNotFoundException(id));
        validateDate(consultation.getDateConsultation());
        ensureRendezvousExists(consultation.getRendezvousId());

        existing.setRendezvousId(consultation.getRendezvousId());
        existing.setDateConsultation(consultation.getDateConsultation());
        existing.setRapport(consultation.getRapport());
        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ConsultationNotFoundException(id);
        }
        repository.deleteById(id);
    }

    private void validateDate(LocalDateTime date) {
        if (date == null) {
            throw new BusinessException("La date de consultation est obligatoire.");
        }
        if (date.isAfter(LocalDateTime.now())) {
            throw new BusinessException("Date de consultation invalide.");
        }
    }

    private void ensureRendezvousExists(Long rendezvousId) {
        if (rendezvousId == null) {
            throw new BusinessException("Rendez-vous introuvable.");
        }
        try {
            restTemplate.getForEntity(RENDEZVOUS_URL, Object.class, rendezvousId);
        } catch (HttpClientErrorException ex) {
            HttpStatusCode status = ex.getStatusCode();
            if (status != null && status.value() == 404) {
                throw new BusinessException("Rendez-vous introuvable.");
            }
        }
    }
}
