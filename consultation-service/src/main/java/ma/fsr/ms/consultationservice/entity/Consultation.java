package ma.fsr.ms.consultationservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "consultations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Rendez-vous introuvable.")
    private Long rendezvousId;

    @NotNull(message = "La date de consultation est obligatoire.")
    @PastOrPresent(message = "Date de consultation invalide.")
    private LocalDateTime dateConsultation;

    @NotNull(message = "Rapport de consultation insuffisant.")
    @Size(min = 10, message = "Rapport de consultation insuffisant.")
    private String rapport;
}
