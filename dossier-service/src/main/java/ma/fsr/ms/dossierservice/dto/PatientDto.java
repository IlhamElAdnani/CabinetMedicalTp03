package ma.fsr.ms.dossierservice.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class PatientDto {
    private Long id;
    private String nom;
    private String telephone;
    private LocalDate dateNaissance;
}
