package ma.fsr.ms.medecinservice.service;

import java.util.List;
import ma.fsr.ms.medecinservice.entity.Medecin;
import ma.fsr.ms.medecinservice.exception.MedecinNotFoundException;
import ma.fsr.ms.medecinservice.repository.MedecinRepository;
import org.springframework.stereotype.Service;

@Service
public class MedecinService {

    private final MedecinRepository repository;

    public MedecinService(MedecinRepository repository) {
        this.repository = repository;
    }

    public List<Medecin> getAll() {
        return repository.findAll();
    }

    public Medecin getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new MedecinNotFoundException(id));
    }

    public Medecin create(Medecin medecin) {
        medecin.setId(null);
        return repository.save(medecin);
    }

    public Medecin update(Long id, Medecin medecin) {
        Medecin existing = repository.findById(id).orElseThrow(() -> new MedecinNotFoundException(id));
        existing.setNom(medecin.getNom());
        existing.setEmail(medecin.getEmail());
        existing.setSpecialite(medecin.getSpecialite());
        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new MedecinNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
