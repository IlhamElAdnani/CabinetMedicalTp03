package ma.fsr.ms.dossierservice.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ConsultationDto {
    private Long id;
    private Long rendezvousId;
    private LocalDateTime dateConsultation;
    private String rapport;
}
