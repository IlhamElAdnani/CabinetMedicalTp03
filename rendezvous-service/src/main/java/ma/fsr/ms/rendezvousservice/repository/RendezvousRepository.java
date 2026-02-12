package ma.fsr.ms.rendezvousservice.repository;

import java.util.List;
import ma.fsr.ms.rendezvousservice.entity.Rendezvous;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RendezvousRepository extends JpaRepository<Rendezvous, Long> {
    List<Rendezvous> findByPatientId(Long patientId);

    List<Rendezvous> findByMedecinId(Long medecinId);
}
