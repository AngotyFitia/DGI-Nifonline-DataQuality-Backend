package dgi.nifonline.backend.services.imports;

import dgi.nifonline.backend.dtos.imports.CentreGestionnaireDTO;
import dgi.nifonline.backend.dtos.imports.ImportReportDTO;
import dgi.nifonline.backend.models.Coordonnees;
import dgi.nifonline.backend.models.CentreGestionnaire;
import dgi.nifonline.backend.repositories.CoordonneesRepository;
import dgi.nifonline.backend.repositories.CentreGestionnaireRepository;
import dgi.nifonline.backend.utils.CSVUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CentreGestionnaireService {

    private final CoordonneesRepository coordonneesRepository;
    private final CentreGestionnaireRepository centreGestionnaireRepository;

    public CentreGestionnaireService(CoordonneesRepository coordonneesRepository, CentreGestionnaireRepository centreGestionnaireRepository) {
        this.coordonneesRepository = coordonneesRepository;
        this.centreGestionnaireRepository = centreGestionnaireRepository;
    }

    public ImportReportDTO importer(String chemin) throws Exception {
        List<String[]> lignes = CSVUtil.lireCSV(chemin, 6);
        int succes = 0;
        int echec = 0;
        StringBuilder message = new StringBuilder();

        int lineNumber = 1;
        for (String[] valeurs : lignes) {
            CentreGestionnaireDTO dto = new CentreGestionnaireDTO(valeurs[0].trim(), valeurs[1].trim(), valeurs[2].trim(), valeurs[3].trim(), valeurs[4].trim(), valeurs[5].trim());
            try {
                Coordonnees coordonnees = coordonneesRepository.findByEmail(dto.getCoordonnees(), dto.getCommune()).orElse(null);
                if (centreGestionnaireRepository.findByCodeBureau(dto.getCodeBureau()).isPresent()) {
                    echec++;
                    message.append("Échec: Ligne ").append(lineNumber).append(" → Centre Gestionnaire '").append(dto.getCodeBureau()).append("' existe déjà.\n");
                }else{                    
                    CentreGestionnaire centreGestionnaire = new CentreGestionnaire();
                    centreGestionnaire.setCoordonnees(coordonnees);
                    centreGestionnaire.setCodeBureau(dto.getCodeBureau());
                    centreGestionnaire.setAbreviation(dto.getAbreviation());
                    centreGestionnaire.setNom(dto.getNom());
                    centreGestionnaire.setCompteBancaire(dto.getCompteBancaire());
                    if ("Validé".equals(dto.getEtatImport())) {
                        centreGestionnaire.setEtat(1);
                    } else if ("En attente".equals(dto.getEtatImport())) {
                        centreGestionnaire.setEtat(0);
                    } else {
                        centreGestionnaire.setEtat(-1);
                    }
    
                    centreGestionnaireRepository.save(centreGestionnaire);
                    succes++;
                    message.append("Succès: Ligne ").append(lineNumber).append(" → CentreGestionnaire '").append(dto.getNom()).append("' insérée avec succès.\n");
                }
            } catch (Exception ex) {
                echec++;
                message.append("Échec: Ligne ").append(lineNumber).append(" → ").append(ex.getMessage()).append("\n");
            }
            lineNumber++;
        }
        return new ImportReportDTO(lignes.size(), succes, echec, message.toString());
    }
}