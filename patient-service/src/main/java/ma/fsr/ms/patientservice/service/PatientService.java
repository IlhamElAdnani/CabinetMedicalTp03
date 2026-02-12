package ma.fsr.ms.patientservice.service;

import java.util.List;
import ma.fsr.ms.patientservice.entity.Patient;
import ma.fsr.ms.patientservice.exception.PatientNotFoundException;
import ma.fsr.ms.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public List<Patient> getAll() {
        return repository.findAll();
    }

    public Patient getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
    }

    public Patient create(Patient patient) {
        patient.setId(null);
        return repository.save(patient);
    }

    public Patient update(Long id, Patient patient) {
        Patient existing = repository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
        existing.setNom(patient.getNom());
        existing.setTelephone(patient.getTelephone());
        existing.setDateNaissance(patient.getDateNaissance());
        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new PatientNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
