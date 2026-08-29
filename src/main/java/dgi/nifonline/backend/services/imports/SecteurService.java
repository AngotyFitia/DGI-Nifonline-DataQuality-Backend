package dgi.nifonline.backend.services.imports;

import dgi.nifonline.backend.dtos.imports.SecteurDTO;
import dgi.nifonline.backend.dtos.imports.ImportReportDTO;
import dgi.nifonline.backend.models.Secteur;
import dgi.nifonline.backend.repositories.SecteurRepository;
import dgi.nifonline.backend.utils.CSVUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecteurService {

    private final SecteurRepository secteurRepository;

    public SecteurService(SecteurRepository secteurRepository) {
        this.secteurRepository = secteurRepository;
    }

    public ImportReportDTO importer(String chemin) throws Exception {
        List<String[]> lignes = CSVUtil.lireCSV(chemin, 3);
        int succes = 0;
        int echec = 0;
        StringBuilder message = new StringBuilder();

        int lineNumber = 1;
        for (String[] valeurs : lignes) {
            SecteurDTO dto = new SecteurDTO(valeurs[0].trim(), valeurs[1].trim(), valeurs[2].trim());
            try {
                dto.validate(lineNumber);

                if (secteurRepository.findByIntitule(dto.getIntitule()).isPresent()) {
                    echec++;
                    message.append("Échec: Ligne ").append(lineNumber).append(" → Secteur '").append(dto.getIntitule()).append("' existe déjà.\n");
                } else {
                    Secteur secteur = new Secteur();
                    secteur.setIntitule(dto.getIntitule());
                    secteur.setDescription(dto.getDescription());
                    if ("Validé".equals(dto.getEtat())) {
                        secteur.setEtat(1);
                    } else if ("En attente".equals(dto.getEtat())) {
                        secteur.setEtat(0);
                    } else {
                        secteur.setEtat(-1);
                    }

                    secteurRepository.save(secteur);
                    succes++;
                    message.append("Succès: Ligne ").append(lineNumber).append(" → Secteur '").append(dto.getIntitule()).append("' insérée avec succès.\n");
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
