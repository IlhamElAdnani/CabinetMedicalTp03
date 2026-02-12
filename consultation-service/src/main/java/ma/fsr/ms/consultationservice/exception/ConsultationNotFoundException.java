package ma.fsr.ms.consultationservice.exception;

public class ConsultationNotFoundException extends RuntimeException {

    public ConsultationNotFoundException(Long id) {
        super("Consultation introuvable : id = " + id + ".");
    }
}
