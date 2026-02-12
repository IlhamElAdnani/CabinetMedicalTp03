package ma.fsr.ms.dossierservice.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class RendezvousDto {
    private Long id;
    private LocalDateTime dateRendezvous;
    private Long patientId;
    private Long medecinId;
    private String statut;
}
