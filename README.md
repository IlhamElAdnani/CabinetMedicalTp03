# TP3 — Mise en œuvre d’une Architecture Microservices (REST)
## Cas : Cabinet Médical

**Module** : Systèmes Distribués Basés sur les Microservices  
**Faculté** : Faculté des Sciences de Rabat  
**Master** : IPS  

---

##  Contexte
Ce TP s’inscrit dans la continuité du **TP2 (architecture SOA)**.  
L’objectif est de faire évoluer l’application vers une **architecture Microservices REST**, en respectant les principes fondamentaux des microservices :

- Microservices autonomes  
- Base de données par service  
- Communication REST synchrone  
- Point d’entrée unique via une API Gateway  
- Suppression de tout module partagé de type `repo`  

---

##  Objectifs du TP
- Mettre en place une **architecture microservices** basée sur Spring Boot  
- Créer des **microservices métiers indépendants**  
- Implémenter une **API Gateway** pour l’exposition externe  
- Implémenter un **service composite (Dossier Patient)**  
- Assurer la validation des règles métiers et la gestion des erreurs  

---

##  Architecture Générale

Client  
↓  
API Gateway (8080)  
↓  
- patient-service (8082)  
- medecin-service (8083)  
- rendezvous-service (8084)  
- consultation-service (8085)  
- dossier-service (8086)  

---

## Structure du Projet (Maven Multi-modules)

cabinetMedicalTp3MS  
├── api-gateway  
├── patient-service  
├── medecin-service  
├── rendezvous-service  
├── consultation-service  
├── dossier-service  
└── pom.xml  

Tous les modules utilisent le même `groupId` : **ma.fsr.ms**

---

##  Microservices

### patient-service
- CRUD Patient  
- Validation des données (nom, téléphone, date de naissance)  
- Port : 8082  

API interne :
/internal/api/v1/patients

---

### medecin-service
- CRUD Médecin  
- Validation email et spécialité  
- Port : 8083  

API interne :
/internal/api/v1/medecins

---

### rendezvous-service
- Gestion des rendez-vous  
- Vérification patient/médecin via REST  
- Gestion des statuts  
- Port : 8084  

API interne :
/internal/api/v1/rendezvous

---

### consultation-service
- Gestion des consultations  
- Liaison avec rendez-vous  
- Port : 8085  

API interne :
/internal/api/v1/consultations

---

### dossier-service
- Service composite (agrégation REST)  
- Vue Dossier Patient  
- Port : 8086  

API :
/internal/api/v1/dossiers/patient/{patientId}

---

##  API Gateway
- Port : 8080  
- Routage des appels externes `/api/**` vers `/internal/api/v1/**`  

---

##  Technologies
- Java 21  
- Spring Boot  
- Spring Data JPA  
- Spring Cloud Gateway  
- Maven  
- H2  
- Lombok  

---
## Test :
## 1.Patient :
<img width="900" height="900" alt="patient" src="https://github.com/user-attachments/assets/ca869857-3346-4acc-9417-23c156ad09bc" />
## 2.Medecin :
<img width="900" height="900" alt="Medecin" src="https://github.com/user-attachments/assets/85fd99b9-87eb-4456-a5cc-154e87665a99" />
## 3.RendezVous :
<img width="900" height="900" alt="RV" src="https://github.com/user-attachments/assets/b3f1e3f7-fbeb-4f77-8cf8-659009bad8b3" />
## 4.Consultationm :
<img width="1536" height="900" alt="Consultation" src="https://github.com/user-attachments/assets/1d34f5a9-e75c-4dca-b6e3-559e3738c137" />
## 5.Dossier :
<img width="1000" height="900" alt="Dossier" src="https://github.com/user-attachments/assets/758ea6bb-ba66-46a6-982b-449982561b4d" />


