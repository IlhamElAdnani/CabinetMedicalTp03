package ma.fsr.ms.rendezvousservice.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StatutUpdateRequest {

    @NotBlank(message = "Statut invalide. Valeurs possibles : PLANIFIE, ANNULE, TERMINE.")
    private String statut;
}
