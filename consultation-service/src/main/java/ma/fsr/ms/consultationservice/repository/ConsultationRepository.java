package ma.fsr.ms.consultationservice.repository;

import java.util.List;
import ma.fsr.ms.consultationservice.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findByRendezvousId(Long rendezvousId);
}
