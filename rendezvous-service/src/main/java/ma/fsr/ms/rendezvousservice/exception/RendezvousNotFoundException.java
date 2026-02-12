package ma.fsr.ms.rendezvousservice.exception;

public class RendezvousNotFoundException extends RuntimeException {

    public RendezvousNotFoundException(Long id) {
        super("Rendezvous introuvable : id = " + id + ".");
    }
}
