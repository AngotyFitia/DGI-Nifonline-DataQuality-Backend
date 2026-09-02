package dgi.nifonline.backend.services.imports;

import dgi.nifonline.backend.dtos.imports.SecteurDTO;
import dgi.nifonline.backend.dtos.imports.ImportReportDTO;
import dgi.nifonline.backend.models.Secteur;
import dgi.nifonline.backend.repositories.SecteurRepository;
import dgi.nifonline.backend.utils.CSVUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.ArrayList;

@Service
public class SecteurService {

    private final SecteurRepository secteurRepository;

    public SecteurService(SecteurRepository secteurRepository) {
        this.secteurRepository = secteurRepository;
    }
    
    @Transactional
    public ImportReportDTO importer(String chemin) throws Exception {
        List<String[]> lignes = CSVUtil.lireCSV(chemin, 3);
        int succes = 0;
        int echec = 0;
        StringBuilder message = new StringBuilder();
        List<Secteur> secteursToInsert = new ArrayList<>();
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
                    secteursToInsert.add(secteur);
                    succes++;
                }
            } catch (Exception ex) {
                echec++;
                message.append("Échec: Ligne ").append(lineNumber).append(" → ").append(ex.getMessage()).append("\n");
            }
            lineNumber++;
        }
        if (echec > 0) {
            return new ImportReportDTO(lignes.size(), succes, echec, message.toString());
        }else{
            message.append("Succès: Import terminé! - "+lignes.size()+" données insérées.");
        }
        secteurRepository.saveAll(secteursToInsert);
        return new ImportReportDTO(lignes.size(), succes, echec, message.toString());
    }
}
