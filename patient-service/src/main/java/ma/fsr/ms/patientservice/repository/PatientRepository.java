package ma.fsr.ms.patientservice.repository;

import ma.fsr.ms.patientservice.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
