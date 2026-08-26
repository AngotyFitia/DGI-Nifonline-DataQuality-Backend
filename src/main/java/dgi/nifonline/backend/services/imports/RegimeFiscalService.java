package dgi.nifonline.backend.services.imports;

import dgi.nifonline.backend.dtos.imports.RegimeFiscalDTO;
import dgi.nifonline.backend.dtos.imports.ImportReportDTO;
import dgi.nifonline.backend.models.RegimeFiscal;
import dgi.nifonline.backend.repositories.RegimeFiscalRepository;
import dgi.nifonline.backend.utils.CSVUtil;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class RegimeFiscalService {

    private final RegimeFiscalRepository regimeFiscalRepository;

    public RegimeFiscalService(RegimeFiscalRepository regimeFiscalRepository) {
        this.regimeFiscalRepository = regimeFiscalRepository;
    }

    public ImportReportDTO importer(String chemin) throws Exception {
        List<String[]> lignes = CSVUtil.lireCSV(chemin, 3);
        int succes = 0;
        int echec = 0;
        StringBuilder message = new StringBuilder();

        int lineNumber = 1;
        for (String[] valeurs : lignes) {
            RegimeFiscalDTO dto = new RegimeFiscalDTO(valeurs[0].trim(), valeurs[1].trim(), valeurs[2].trim());
            try {
                dto.validate(lineNumber);

                if (regimeFiscalRepository.findByIntitule(dto.getIntitule()).isPresent()) {
                    echec++;
                    message.append("Échec: Ligne ").append(lineNumber).append(" → RegimeFiscal '").append(dto.getIntitule()).append("' existe déjà.\n");
                } else {
                    RegimeFiscal regimeFiscal = new RegimeFiscal();
                    regimeFiscal.setIntitule(dto.getIntitule());
                    regimeFiscal.setDescription(dto.getDescription());
                    if ("Validé".equals(dto.getEtatImport())) {
                        regimeFiscal.setEtat(1);
                    } else if ("En attente".equals(dto.getEtatImport())) {
                        regimeFiscal.setEtat(0);
                    } else {
                        regimeFiscal.setEtat(-1);
                    }

                    regimeFiscalRepository.save(regimeFiscal);
                    succes++;
                    message.append("Succès: Ligne ").append(lineNumber).append(" → RegimeFiscal '").append(dto.getIntitule()).append("' insérée avec succès.\n");
                }
            } catch (Exception ex) {
                echec++;
                message.append("Échec: Ligne ").append(lineNumber).append(" → ").append(ex.getMessage()).append("\n");
            }
            lineNumber++;
        }
        return new ImportReportDTO(lignes.size(), succes, echec, message.toString());
    }

    public Page<RegimeFiscalDTO> getRegimesFiscaux(String intitule, String description, String etat, Pageable pageable) {
        boolean hasIntitule = !intitule.equals("tous");
        boolean hasDescription = !description.equals("tous");
        boolean hasEtat = !etat.equals("tous");
    
        Page<RegimeFiscal> regimes;
        if (hasIntitule && hasDescription && hasEtat) {
            regimes = regimeFiscalRepository.findByIntituleContainingIgnoreCaseAndDescriptionContainingIgnoreCaseAndEtat(intitule, description, Integer.parseInt(etat), pageable);
        } else if (hasIntitule && hasDescription) {
            regimes = regimeFiscalRepository.findByIntituleContainingIgnoreCaseAndDescriptionContainingIgnoreCase(intitule, description, pageable);
        } else if (hasIntitule) {
            regimes = regimeFiscalRepository.findByIntituleContainingIgnoreCase(intitule, pageable);
        } else {
            regimes = regimeFiscalRepository.findAll(pageable);
        }
        return regimes.map(RegimeFiscalDTO::new);
    }
    
}
