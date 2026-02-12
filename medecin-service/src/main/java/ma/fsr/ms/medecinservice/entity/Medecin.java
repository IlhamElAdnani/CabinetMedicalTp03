package ma.fsr.ms.medecinservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medecins")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medecin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du médecin est obligatoire.")
    private String nom;

    @NotBlank(message = "L'email du médecin est obligatoire.")
    @Email(message = "Email du médecin invalide.")
    private String email;

    @NotBlank(message = "La spécialité du médecin est obligatoire.")
    private String specialite;
}
