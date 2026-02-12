package ma.fsr.ms.rendezvousservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rendezvous")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rendezvous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La date du rendez-vous doit être future.")
    private LocalDateTime dateRendezvous;

    @NotNull(message = "Patient introuvable.")
    private Long patientId;

    @NotNull(message = "Médecin introuvable.")
    private Long medecinId;

    @Enumerated(EnumType.STRING)
    private RendezvousStatut statut;
}
