package ma.fsr.ms.dossierservice.dto;

import lombok.Data;

@Data
public class MedecinDto {
    private Long id;
    private String nom;
    private String email;
    private String specialite;
}
