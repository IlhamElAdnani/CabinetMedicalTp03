package ma.fsr.ms.dossierservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import ma.fsr.ms.dossierservice.dto.ConsultationDto;
import ma.fsr.ms.dossierservice.dto.DossierPatientDto;
import ma.fsr.ms.dossierservice.dto.MedecinDto;
import ma.fsr.ms.dossierservice.dto.PatientDto;
import ma.fsr.ms.dossierservice.dto.RendezvousDto;
import ma.fsr.ms.dossierservice.dto.RendezvousItemDto;
import ma.fsr.ms.dossierservice.exception.BusinessException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class DossierService {

    private static final String PATIENT_URL = "http://localhost:8082/internal/api/v1/patients/{id}";
    private static final String RENDEZVOUS_BY_PATIENT_URL =
            "http://localhost:8084/internal/api/v1/rendezvous/patient/{id}";
    private static final String MEDECIN_URL = "http://localhost:8083/internal/api/v1/medecins/{id}";
    private static final String CONSULTATIONS_BY_RENDEZVOUS_URL =
            "http://localhost:8085/internal/api/v1/consultations/rendezvous/{id}";

    private final RestTemplate restTemplate;

    public DossierService() {
        this.restTemplate = new RestTemplate();
    }

    public DossierPatientDto getDossierByPatientId(Long patientId) {
        PatientDto patient = fetchPatient(patientId);
        List<RendezvousDto> rendezvous = fetchRendezvousByPatient(patientId);

        List<RendezvousItemDto> items = new ArrayList<>();
        for (RendezvousDto rdv : rendezvous) {
            RendezvousItemDto item = new RendezvousItemDto();
            item.setRendezvous(rdv);
            item.setMedecin(fetchMedecin(rdv.getMedecinId()));
            item.setConsultations(fetchConsultationsByRendezvous(rdv.getId()));
            items.add(item);
        }

        DossierPatientDto dossier = new DossierPatientDto();
        dossier.setPatient(patient);
        dossier.setRendezvous(items);
        return dossier;
    }

    private PatientDto fetchPatient(Long patientId) {
        try {
            return restTemplate.getForObject(PATIENT_URL, PatientDto.class, patientId);
        } catch (HttpClientErrorException ex) {
            HttpStatusCode status = ex.getStatusCode();
            if (status != null && status.value() == 404) {
                throw new BusinessException("Patient introuvable.");
            }
            throw ex;
        }
    }

    private List<RendezvousDto> fetchRendezvousByPatient(Long patientId) {
        try {
            RendezvousDto[] array =
                    restTemplate.getForObject(RENDEZVOUS_BY_PATIENT_URL, RendezvousDto[].class, patientId);
            if (array == null) {
                return Collections.emptyList();
            }
            return Arrays.asList(array);
        } catch (HttpClientErrorException ex) {
            return Collections.emptyList();
        }
    }

    private MedecinDto fetchMedecin(Long medecinId) {
        try {
            return restTemplate.getForObject(MEDECIN_URL, MedecinDto.class, medecinId);
        } catch (HttpClientErrorException ex) {
            return null;
        }
    }

    private List<ConsultationDto> fetchConsultationsByRendezvous(Long rendezvousId) {
        try {
            ConsultationDto[] array = restTemplate.getForObject(
                    CONSULTATIONS_BY_RENDEZVOUS_URL, ConsultationDto[].class, rendezvousId);
            if (array == null) {
                return Collections.emptyList();
            }
            return Arrays.asList(array);
        } catch (HttpClientErrorException ex) {
            return Collections.emptyList();
        }
    }
}
