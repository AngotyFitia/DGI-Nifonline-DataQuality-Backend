package dgi.nifonline.backend.services.imports;

import dgi.nifonline.backend.dtos.imports.RegionDTO;
import dgi.nifonline.backend.dtos.imports.ImportReportDTO;
import dgi.nifonline.backend.models.Region;
import dgi.nifonline.backend.models.Province;
import dgi.nifonline.backend.repositories.RegionRepository;
import dgi.nifonline.backend.repositories.ProvinceRepository;
import dgi.nifonline.backend.utils.CSVUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class RegionService {

    private final RegionRepository regionRepository;
    private final ProvinceRepository provinceRepository;

    public RegionService(RegionRepository regionRepository, ProvinceRepository provinceRepository) {
        this.regionRepository = regionRepository;
        this.provinceRepository = provinceRepository;
    }

    @Transactional
    public ImportReportDTO importer(String chemin) throws Exception {
        List<String[]> lignes = CSVUtil.lireCSV(chemin, 3);
        int succes = 0;
        int echec = 0;
        StringBuilder message = new StringBuilder();
        List<Region> regionsToInsert = new ArrayList<>();
        int lineNumber = 1;
        for (String[] valeurs : lignes) {
            RegionDTO dto = new RegionDTO(valeurs[0].trim(), valeurs[1].trim(), valeurs[2].trim());
            try {
                dto.validate(lineNumber);

                Province province = provinceRepository.findByIntitule(dto.getProvinceIntitule()) .orElse(null);
                if (province == null) {
                    echec++;
                    message.append("Échec: Ligne ").append(lineNumber)
                           .append(" → Province '").append(dto.getProvinceIntitule())
                           .append("' inexistante.\n");
                } else if (regionRepository.findByIntitule(dto.getIntitule()).isPresent()) {
                    echec++;
                    message.append("Échec: Ligne ").append(lineNumber)
                           .append(" → Région '").append(dto.getIntitule())
                           .append("' existe déjà.\n");
                } else {
                    Region region = new Region();
                    region.setIntitule(dto.getIntitule());
                    region.setProvince(province);

                    if ("Validé".equals(dto.getEtat())) {
                        region.setEtat(1);
                    } else if ("En attente".equals(dto.getEtat())) {
                        region.setEtat(0);
                    } else {
                        region.setEtat(-1);
                    }
                    regionsToInsert.add(region);
                    succes++;
                    message.append("Succès: Ligne ").append(lineNumber).append(" → Région '").append(dto.getIntitule()).append("' insérée avec succès.\n");
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
        regionRepository.saveAll(regionsToInsert);
        return new ImportReportDTO(lignes.size(), succes, echec, message.toString());
    }
}
