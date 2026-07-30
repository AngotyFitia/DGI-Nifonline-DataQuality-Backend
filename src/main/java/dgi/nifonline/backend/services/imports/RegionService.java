package dgi.nifonline.backend.services.imports;

import dgi.nifonline.backend.dtos.imports.RegionDTO;
import dgi.nifonline.backend.dtos.imports.ImportReportDTO;
import dgi.nifonline.backend.models.Region;
import dgi.nifonline.backend.models.Province;
import dgi.nifonline.backend.repositories.RegionRepository;
import dgi.nifonline.backend.repositories.ProvinceRepository;
import dgi.nifonline.backend.utils.CSVUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {

    private final RegionRepository regionRepository;
    private final ProvinceRepository provinceRepository;

    public RegionService(RegionRepository regionRepository, ProvinceRepository provinceRepository) {
        this.regionRepository = regionRepository;
        this.provinceRepository = provinceRepository;
    }

    public ImportReportDTO importer(String chemin) throws Exception {
        List<RegionDTO> dtos = CSVUtil.lireCSVRegion(chemin); 
        int succes = 0;
        int echec = 0;
        StringBuilder message = new StringBuilder();

        int lineNumber = 1;
        for (RegionDTO dto : dtos) {
            dto.validate(lineNumber);

            Province province = provinceRepository.findByIntitule(dto.getProvinceIntitule()).orElse(null);
            if (province == null) {
                echec++;
                message.append("Échec: Ligne ").append(lineNumber)
                       .append(" → Province '").append(dto.getProvinceIntitule())
                       .append("' inexistante. Insérez d'abord la province.\n");
            } else if (regionRepository.findByIntitule(dto.getIntitule()).isPresent()) {
                echec++;
                message.append("Échec: Ligne ").append(lineNumber)
                       .append(" → Région '").append(dto.getIntitule())
                       .append("' existe déjà.\n");
            } else {
                Region region = new Region();
                region.setIntitule(dto.getIntitule());
                if ("Validé".equals(dto.getEtat())) {
                    region.setEtat(1);
                } else if ("En attente".equals(dto.getEtat())) {
                    region.setEtat(0);
                } else {
                    region.setEtat(-1);
                }
                region.setProvince(province);

                regionRepository.save(region);
                succes++;
                message.append("Succès: Ligne ").append(lineNumber)
                       .append(" → Région '").append(dto.getIntitule())
                       .append("' insérée avec succès.\n");
            }
            lineNumber++;
        }
        return new ImportReportDTO(dtos.size(), succes, echec, message.toString());
    }
}
