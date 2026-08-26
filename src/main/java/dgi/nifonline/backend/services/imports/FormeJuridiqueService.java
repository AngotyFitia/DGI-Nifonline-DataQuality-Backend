package dgi.nifonline.backend.services.imports;

import dgi.nifonline.backend.dtos.imports.FormeJuridiqueDTO;
import dgi.nifonline.backend.dtos.imports.ImportReportDTO;
import dgi.nifonline.backend.models.FormeJuridique;
import dgi.nifonline.backend.repositories.FormeJuridiqueRepository;
import dgi.nifonline.backend.utils.CSVUtil;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class FormeJuridiqueService {

    private final FormeJuridiqueRepository formeJuridiqueRepository;

    public FormeJuridiqueService(FormeJuridiqueRepository formeJuridiqueRepository) {
        this.formeJuridiqueRepository = formeJuridiqueRepository;
    }

    public ImportReportDTO importer(String chemin) throws Exception {
        List<String[]> lignes = CSVUtil.lireCSV(chemin, 4);
        int succes = 0;
        int echec = 0;
        StringBuilder message = new StringBuilder();

        int lineNumber = 1;
        for (String[] valeurs : lignes) {
            FormeJuridiqueDTO dto = new FormeJuridiqueDTO(valeurs[0].trim(), valeurs[1].trim(), valeurs[2].trim(), valeurs[3].trim());
            try {
                dto.validate(lineNumber);

                if (formeJuridiqueRepository.findByIntitule(dto.getIntitule()).isPresent()) {
                    echec++;
                    message.append("Échec: Ligne ").append(lineNumber).append(" → FormeJuridique '").append(dto.getIntitule()).append("' existe déjà.\n");
                } else {
                    FormeJuridique formeJuridique = new FormeJuridique();
                    formeJuridique.setAbreviation(dto.getAbreviation());
                    formeJuridique.setIntitule(dto.getIntitule());
                    formeJuridique.setDescription(dto.getDescription());
                    if ("Validé".equals(dto.getEtatImport())) {
                        formeJuridique.setEtat(1);
                    } else if ("En attente".equals(dto.getEtatImport())) {
                        formeJuridique.setEtat(0);
                    } else {
                        formeJuridique.setEtat(-1);
                    }

                    formeJuridiqueRepository.save(formeJuridique);
                    succes++;
                    message.append("Succès: Ligne ").append(lineNumber).append(" → FormeJuridique '").append(dto.getIntitule()).append("' insérée avec succès.\n");
                }
            } catch (Exception ex) {
                echec++;
                message.append("Échec: Ligne ").append(lineNumber).append(" → ").append(ex.getMessage()).append("\n");
            }
            lineNumber++;
        }
        return new ImportReportDTO(lignes.size(), succes, echec, message.toString());
    }

    public Page<FormeJuridiqueDTO> getFormesJuridique(String abreviation, String intitule, String description, String etat, Pageable pageable) {
        boolean hasAbreviation = !abreviation.equals("tous");
        boolean hasIntitule = !intitule.equals("tous");
        boolean hasDescription = !description.equals("tous");
        boolean hasEtat = !etat.equals("tous");
    
        Page<FormeJuridique> formeJuridiques;
        if (hasAbreviation && hasIntitule && hasDescription && hasEtat) {
            formeJuridiques = formeJuridiqueRepository.findByAbreviationContainingIgnoreCaseAndIntituleContainingIgnoreCaseAndDescriptionContainingIgnoreCaseAndEtat(abreviation, intitule, description, Integer.parseInt(etat), pageable);
        } else if (hasIntitule && hasDescription) {
            formeJuridiques = formeJuridiqueRepository.findByIntituleContainingIgnoreCaseAndDescriptionContainingIgnoreCase(intitule, description, pageable);
        } else if (hasIntitule) {
            formeJuridiques = formeJuridiqueRepository.findByIntituleContainingIgnoreCase(intitule, pageable);
        } else if (hasAbreviation) {
            formeJuridiques = formeJuridiqueRepository.findByAbreviationContainingIgnoreCase(abreviation, pageable);
        } 
        else if (hasDescription) {
            formeJuridiques = formeJuridiqueRepository.findByDescriptionContainingIgnoreCase(description, pageable);
        } 
        else if (hasEtat) {
            formeJuridiques = formeJuridiqueRepository.findByEtatContainingIgnoreCase( Integer.parseInt(etat), pageable);
        } 
        else {
            formeJuridiques = formeJuridiqueRepository.findAll(pageable);
        }
        return formeJuridiques.map(FormeJuridiqueDTO::new);
    }
}
