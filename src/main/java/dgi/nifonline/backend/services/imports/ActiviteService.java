package dgi.nifonline.backend.services.imports;

import dgi.nifonline.backend.dtos.imports.ActiviteDTO;
import dgi.nifonline.backend.dtos.imports.ImportReportDTO;
import dgi.nifonline.backend.models.Activite;
import dgi.nifonline.backend.models.Secteur;
import dgi.nifonline.backend.repositories.ActiviteRepository;
import dgi.nifonline.backend.repositories.SecteurRepository;
import dgi.nifonline.backend.utils.CSVUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActiviteService {

    private final ActiviteRepository activiteRepository;
    private final SecteurRepository secteurRepository;

    public ActiviteService(ActiviteRepository activiteRepository, SecteurRepository secteurRepository) {
        this.activiteRepository = activiteRepository;
        this.secteurRepository = secteurRepository;
    }

    public ImportReportDTO importer(String chemin) throws Exception {
        List<String[]> lignes = CSVUtil.lireCSV(chemin, 5);
        int succes = 0;
        int echec = 0;
        StringBuilder message = new StringBuilder();

        int lineNumber = 1;
        for (String[] valeurs : lignes) {
            ActiviteDTO dto = new ActiviteDTO(valeurs[0].trim(), valeurs[1].trim(), valeurs[2].trim(), valeurs[3].trim(), valeurs[4].trim());
            try {
                dto.validate(lineNumber);
                Secteur secteur = secteurRepository.findByIntitule(dto.getSecteurIntitule()) .orElse(null);
                if (secteur == null) {
                    echec++;
                    message.append("Échec: Ligne ").append(lineNumber).append(" → Secteur '").append(dto.getSecteurIntitule()).append("' inexistant.\n");
                } else if (activiteRepository.findByIntituleAndSecteurAndSection(dto.getIntitule(), secteur, dto.getSection()).isPresent()) {
                    echec++;
                    message.append("Échec: Ligne ").append(lineNumber).append(" → Activite '").append(dto.getIntitule()).append("' existe déjà.\n");
                } else {
                    Activite activite = new Activite();
                    activite.setIntitule(dto.getIntitule());
                    activite.setSecteur(secteur);
                    activite.setSection(dto.getSection());
                    activite.setCodeActivite(Integer.valueOf(dto.getCodeActivite()));
                    if ("Validé".equals(dto.getEtat())) {
                        activite.setEtat(1);
                    } else if ("En attente".equals(dto.getEtat())) {
                        activite.setEtat(0);
                    } else {
                        activite.setEtat(-1);
                    }
                    activiteRepository.save(activite);
                    succes++;
                    message.append("Succès: Ligne ").append(lineNumber).append(" → Activite '").append(dto.getIntitule()).append("' insérée avec succès dans le secteur '").append(secteur.getIntitule()).append("'.\n");
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