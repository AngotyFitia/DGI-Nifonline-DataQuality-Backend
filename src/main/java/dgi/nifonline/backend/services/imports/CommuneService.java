package dgi.nifonline.backend.services.imports;

import dgi.nifonline.backend.dtos.imports.CommuneDTO;
import dgi.nifonline.backend.dtos.CommuneListeDTO;
import dgi.nifonline.backend.dtos.imports.ImportReportDTO;
import dgi.nifonline.backend.models.Commune;
import dgi.nifonline.backend.models.District;
import dgi.nifonline.backend.repositories.CommuneRepository;
import dgi.nifonline.backend.repositories.DistrictRepository;
import dgi.nifonline.backend.utils.CSVUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommuneService {

    private final CommuneRepository communeRepository;
    private final DistrictRepository districtRepository;

    public CommuneService(CommuneRepository communeRepository, DistrictRepository districtRepository) {
        this.communeRepository = communeRepository;
        this.districtRepository = districtRepository;
    }

    public ImportReportDTO importer(String chemin) throws Exception {
        List<String[]> lignes = CSVUtil.lireCSV(chemin, 3);
        int succes = 0;
        int echec = 0;
        StringBuilder message = new StringBuilder();

        int lineNumber = 1;
        for (String[] valeurs : lignes) {
            CommuneDTO dto = new CommuneDTO(valeurs[0].trim(), valeurs[1].trim(), valeurs[2].trim());
            try {
                dto.validate(lineNumber);
                District district = districtRepository.findByIntitule(dto.getDistrictIntitule()) .orElse(null);
                if (district == null) {
                    echec++;
                    message.append("Échec: Ligne ").append(lineNumber).append(" → District '").append(dto.getDistrictIntitule()).append("' inexistante.\n");
                } else if (communeRepository.findByIntituleAndDistrict(dto.getIntitule(), district).isPresent()) {
                    echec++;
                    message.append("Échec: Ligne ").append(lineNumber).append(" → Commune '").append(dto.getIntitule()).append("' existe déjà.\n");
                } else {
                    Commune commune = new Commune();
                    commune.setIntitule(dto.getIntitule());
                    commune.setDistrict(district);
                    if ("Validé".equals(dto.getEtat())) {
                        commune.setEtat(1);
                    } else if ("En attente".equals(dto.getEtat())) {
                        commune.setEtat(0);
                    } else {
                        commune.setEtat(-1);
                    }

                    communeRepository.save(commune);
                    succes++;
                    message.append("Succès: Ligne ").append(lineNumber).append(" → Commune '").append(dto.getIntitule()).append("' insérée avec succès dans le district '")
                    .append(district.getIntitule()).append("'.\n");
                }
            } catch (Exception ex) {
                echec++;
                message.append("Échec: Ligne ").append(lineNumber).append(" → ").append(ex.getMessage()).append("\n");
            }
            lineNumber++;
        }
        return new ImportReportDTO(lignes.size(), succes, echec, message.toString());
    }

    public List<CommuneListeDTO> getCommunesAvecHierarchie() {
        return communeRepository.findAll().stream().map(CommuneListeDTO::new).toList();
    }
}