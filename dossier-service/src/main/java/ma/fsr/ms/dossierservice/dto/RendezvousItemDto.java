package ma.fsr.ms.dossierservice.dto;

import java.util.List;
import lombok.Data;

@Data
public class RendezvousItemDto {
    private RendezvousDto rendezvous;
    private MedecinDto medecin;
    private List<ConsultationDto> consultations;
}
