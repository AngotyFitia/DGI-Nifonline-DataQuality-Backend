package dgi.nifonline.backend.services.imports;

import dgi.nifonline.backend.dtos.imports.TypeImpotDTO;
import dgi.nifonline.backend.dtos.imports.ImportReportDTO;
import dgi.nifonline.backend.models.TypeImpot;
import dgi.nifonline.backend.repositories.TypeImpotRepository;
import dgi.nifonline.backend.utils.CSVUtil;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class TypeImpotService {

    private final TypeImpotRepository typeImpotRepository;

    public TypeImpotService(TypeImpotRepository typeImpotRepository) {
        this.typeImpotRepository = typeImpotRepository;
    }

    public ImportReportDTO importer(String chemin) throws Exception {
        List<String[]> lignes = CSVUtil.lireCSV(chemin, 3);
        int succes = 0;
        int echec = 0;
        StringBuilder message = new StringBuilder();

        int lineNumber = 1;
        for (String[] valeurs : lignes) {
            TypeImpotDTO dto = new TypeImpotDTO(valeurs[0].trim(), valeurs[1].trim(), valeurs[2].trim());
            try {
                dto.validate(lineNumber);

                if (typeImpotRepository.findByIntitule(dto.getIntitule()).isPresent()) {
                    echec++;
                    message.append("Échec: Ligne ").append(lineNumber).append(" → TypeImpot '").append(dto.getIntitule()).append("' existe déjà.\n");
                } else {
                    TypeImpot typeImpot = new TypeImpot();
                    typeImpot.setCode(dto.getCode());
                    typeImpot.setIntitule(dto.getIntitule());
                    if ("Validé".equals(dto.getEtatImport())) {
                        typeImpot.setEtat(1);
                    } else if ("En attente".equals(dto.getEtatImport())) {
                        typeImpot.setEtat(0);
                    } else {
                        typeImpot.setEtat(-1);
                    }

                    typeImpotRepository.save(typeImpot);
                    succes++;
                    message.append("Succès: Ligne ").append(lineNumber).append(" → TypeImpot '").append(dto.getIntitule()).append("' insérée avec succès.\n");
                }
            } catch (Exception ex) {
                echec++;
                message.append("Échec: Ligne ").append(lineNumber).append(" → ").append(ex.getMessage()).append("\n");
            }
            lineNumber++;
        }
        return new ImportReportDTO(lignes.size(), succes, echec, message.toString());
    }

    public Page<TypeImpotDTO> getTypesImpots(String code, String intitule, String etat, Pageable pageable) {
        boolean hasIntitule = !intitule.equals("tous");
        boolean hasCode = !code.equals("tous");
        boolean hasEtat = !etat.equals("tous");
    
        Page<TypeImpot> typesImpots;
        if (hasIntitule && hasCode && hasEtat) {
            typesImpots = typeImpotRepository.findByIntituleContainingIgnoreCaseAndCodeContainingIgnoreCaseAndEtat(intitule, code, Integer.parseInt(etat), pageable);
        } else if (hasIntitule && hasCode) {
            typesImpots = typeImpotRepository.findByIntituleContainingIgnoreCaseAndCodeContainingIgnoreCase(intitule, code, pageable);
        } else if (hasIntitule) {
            typesImpots = typeImpotRepository.findByIntituleContainingIgnoreCase(intitule, pageable);
        } else if (hasCode) {
            typesImpots = typeImpotRepository.findByCodeContainingIgnoreCase(code, pageable);
        }
        else if (hasEtat) {
            typesImpots = typeImpotRepository.findByEtatContainingIgnoreCase(Integer.parseInt(etat), pageable);
        }
        else {
            typesImpots = typeImpotRepository.findAll(pageable);
        }
        return typesImpots.map(TypeImpotDTO::new);
    }
    
}
