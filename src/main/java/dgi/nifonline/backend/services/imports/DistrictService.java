package dgi.nifonline.backend.services.imports;

import dgi.nifonline.backend.dtos.imports.DistrictDTO;
import dgi.nifonline.backend.dtos.imports.ImportReportDTO;
import dgi.nifonline.backend.models.District;
import dgi.nifonline.backend.models.Region;
import dgi.nifonline.backend.repositories.DistrictRepository;
import dgi.nifonline.backend.repositories.RegionRepository;
import dgi.nifonline.backend.utils.CSVUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictService {

    private final DistrictRepository districtRepository;
    private final RegionRepository regionRepository;

    public DistrictService(DistrictRepository districtRepository, RegionRepository regionRepository) {
        this.districtRepository = districtRepository;
        this.regionRepository = regionRepository;
    }

    public ImportReportDTO importer(String chemin) throws Exception {
        List<String[]> lignes = CSVUtil.lireCSV(chemin, 3);
        int succes = 0;
        int echec = 0;
        StringBuilder message = new StringBuilder();

        int lineNumber = 1;
        for (String[] valeurs : lignes) {
            DistrictDTO dto = new DistrictDTO(valeurs[0].trim(), valeurs[1].trim(), valeurs[2].trim());
            try {
                dto.validate(lineNumber);
                Region region = regionRepository.findByIntitule(dto.getRegionIntitule()) .orElse(null);
                if (region == null) {
                    echec++;
                    message.append("Échec: Ligne ").append(lineNumber).append(" → Région '").append(dto.getRegionIntitule()).append("' inexistante.\n");
                } else if (districtRepository.findByIntitule(dto.getIntitule()).isPresent()) {
                    echec++;
                    message.append("Échec: Ligne ").append(lineNumber).append(" → District '").append(dto.getIntitule()).append("' existe déjà.\n");
                } else {
                    District district = new District();
                    district.setIntitule(dto.getIntitule());
                    district.setRegion(region);

                    if ("Validé".equals(dto.getEtat())) {
                        district.setEtat(1);
                    } else if ("En attente".equals(dto.getEtat())) {
                        district.setEtat(0);
                    } else {
                        district.setEtat(-1);
                    }

                    districtRepository.save(district);
                    succes++;
                    message.append("Succès: Ligne ").append(lineNumber).append(" → District '").append(dto.getIntitule()).append("' insérée avec succès.\n");
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