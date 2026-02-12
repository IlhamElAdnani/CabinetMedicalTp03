package ma.fsr.ms.dossierservice.dto;

import java.util.List;
import lombok.Data;

@Data
public class DossierPatientDto {
    private PatientDto patient;
    private List<RendezvousItemDto> rendezvous;
}
