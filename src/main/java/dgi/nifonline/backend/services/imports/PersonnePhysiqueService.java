package dgi.nifonline.backend.services.imports;

import dgi.nifonline.backend.dtos.imports.CoordonneesDTO;
import dgi.nifonline.backend.dtos.imports.PersonnePhysiqueDTO;
import dgi.nifonline.backend.dtos.imports.ImportReportDTO;
import dgi.nifonline.backend.models.Coordonnees;
import dgi.nifonline.backend.models.Commune;
import dgi.nifonline.backend.models.District;
import dgi.nifonline.backend.models.Personne;
import dgi.nifonline.backend.models.Sexe;
import dgi.nifonline.backend.models.PersonnePhysique;
import dgi.nifonline.backend.repositories.CoordonneesRepository;
import dgi.nifonline.backend.repositories.CommuneRepository;
import dgi.nifonline.backend.repositories.DistrictRepository;
import dgi.nifonline.backend.repositories.PersonnePhysiqueRepository;
import dgi.nifonline.backend.repositories.PersonneRepository;
import dgi.nifonline.backend.repositories.SexeRepository;
import dgi.nifonline.backend.utils.CSVUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;


@Service
public class PersonnePhysiqueService {

    private final CoordonneesRepository coordonneesRepository;
    private final PersonneRepository personneRepository;
    private final PersonnePhysiqueRepository personnePhysiqueRepository;
    private final CommuneRepository communeRepository;
    private final DistrictRepository districtRepository;
    private final SexeRepository sexeRepository;

    public PersonnePhysiqueService(CoordonneesRepository coordonneesRepository,
                                         PersonneRepository personneRepository,
                                         PersonnePhysiqueRepository personnePhysiqueRepository,
                                         CommuneRepository communeRepository,
                                         DistrictRepository districtRepository,
                                        SexeRepository sexeRepository) {
        this.coordonneesRepository = coordonneesRepository;
        this.personneRepository = personneRepository;
        this.personnePhysiqueRepository = personnePhysiqueRepository;
        this.communeRepository = communeRepository;
        this.districtRepository = districtRepository;
        this.sexeRepository= sexeRepository;
    }

    @Transactional
    public ImportReportDTO importer(String cheminCoordonnees, String cheminPersonnes) throws Exception {
        List<String[]> lignesCoord = CSVUtil.lireCSV(cheminCoordonnees, 11);
        List<String[]> lignesPers = CSVUtil.lireCSV(cheminPersonnes, 7);

        int succes = 0;
        int echec = 0;
        StringBuilder message = new StringBuilder();
        List<Coordonnees> coordonneesToInsert = new ArrayList<>();
        List<Personne> personnesToInsert = new ArrayList<>();
        List<PersonnePhysique> personnesPhysiquesToInsert = new ArrayList<>();
        for (String[] valeurs : lignesCoord) {
            try {
                District district = districtRepository.findByIntitule(valeurs[0]).orElse(null);
                Commune commune = communeRepository.findByIntituleAndDistrict(valeurs[1], district).orElse(null);
                Coordonnees coordonnees = new Coordonnees();
                coordonnees.setCommune(commune);
                coordonnees.setEmail(valeurs[2]);
                coordonnees.setTelephone(valeurs[3]);
                coordonnees.setTelephoneSecondaire(valeurs[4]);
                coordonnees.setSiteWeb(valeurs[5]);
                coordonnees.setAdresse(valeurs[6]);
                coordonnees.setCodePostal(Integer.parseInt(valeurs[7]));
                coordonnees.setLatitude(Double.parseDouble(valeurs[8]));
                coordonnees.setLongitude(Double.parseDouble(valeurs[9]));
                coordonnees.setEtat("Validé".equals(valeurs[10]) ? 1 : 0);
                coordonneesToInsert.add(coordonnees);
                succes++;
            } catch (Exception ex) {
                echec++;
                message.append("Échec coordonnees → ").append(ex.getMessage()).append("\n");
            }
        }

        for (String[] valeurs : lignesPers) {
            try {
                Coordonnees coord = coordonneesRepository.findByEmail(valeurs[6]).orElse(null);
                if (coord == null) {
                    echec++;
                    message.append("Échec: Pas de coordonnées pour email ").append(valeurs[6]).append("\n");
                    continue;
                }

                Personne personne = new Personne();
                personne.setCoordonnees(coord);
                personne.setTypePersonne(1);
                personnesToInsert.add(personne);

                PersonnePhysique personnePhysique = new PersonnePhysique();
                personnePhysique.setPersonne(personne);
                personnePhysique.setNom(valeurs[0]);
                personnePhysique.setPrenoms(valeurs[1]);
                personnePhysique.setDateNaissance(java.sql.Date.valueOf(valeurs[2]));
                if ("0".equals(valeurs[3])) {
                    personnePhysique.setSexe(sexeRepository.findByIntitule("Femme").orElse(null));
                } else {
                    personnePhysique.setSexe(sexeRepository.findByIntitule("Homme").orElse(null));
                }
                personnePhysique.setStatutMatrimonial(null);
                personnePhysique.setNumeroCIN(valeurs[4]);
                personnePhysique.setNumeroPasseport(valeurs[5]);
                personnesPhysiquesToInsert.add(personnePhysique);
                succes++;
                message.append("Succès: Personne ").append(valeurs[0]).append(" insérée.\n");
            } catch (Exception ex) {
                echec++;
                message.append("Échec personne → ").append(ex.getMessage()).append("\n");
            }
        }
        if (echec > 0) {
            return new ImportReportDTO(lignesCoord.size() + lignesPers.size(), succes, echec, message.toString());
        }

        coordonneesRepository.saveAll(coordonneesToInsert);
        personneRepository.saveAll(personnesToInsert);
        personnePhysiqueRepository.saveAll(personnesPhysiquesToInsert);
        message.append("Succès: Import terminé! - ").append(lignesCoord.size()+" - coordonnées et" + lignesPers.size()+ " - personnes physiques,").append(" insérées.");
        return new ImportReportDTO(lignesCoord.size() + lignesPers.size(), succes, echec, message.toString());
    }

    public List<PersonnePhysiqueDTO> getAllPersonnesPhysiques() {
        return personnePhysiqueRepository.findAllWithCoordonnees().stream().map(PersonnePhysiqueDTO::new).toList();
    }
}

