package ma.fsr.ms.patientservice.exception;

public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException(Long id) {
        super("Patient introuvable : id = " + id + ".");
    }
}
