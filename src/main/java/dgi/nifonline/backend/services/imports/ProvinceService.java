package dgi.nifonline.backend.services.imports;

import dgi.nifonline.backend.dtos.imports.ProvinceDTO;
import dgi.nifonline.backend.dtos.imports.ImportReportDTO;
import dgi.nifonline.backend.models.Province;
import dgi.nifonline.backend.repositories.ProvinceRepository;
import dgi.nifonline.backend.utils.CSVUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceService {

    private final ProvinceRepository provinceRepository;

    public ProvinceService(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    public ImportReportDTO importer(String chemin) throws Exception {
        List<ProvinceDTO> dtos = CSVUtil.lireCSV(chemin);
        int succes = 0;
        int echec = 0;
        StringBuilder message = new StringBuilder();

        for (ProvinceDTO dto : dtos) {
            if (provinceRepository.findByIntitule(dto.getIntitule()).isPresent()) {
                echec++;
                message.append("Échec: Province '") .append(dto.getIntitule()).append("' existe déjà.\n");
            } else {
                Province province = new Province();
                province.setIntitule(dto.getIntitule());
                if ("Validé".equals(dto.getEtat())) {
                    province.setEtat(1);
                } else if ("En attente".equals(dto.getEtat())) {
                    province.setEtat(0);
                } else {
                    province.setEtat(-1);
                }
                provinceRepository.save(province);
                succes++;
                message.append("Succès: Province '").append(dto.getIntitule()).append("' insérée avec succès.\n");
            }
        }
        return new ImportReportDTO(dtos.size(), succes, echec, message.toString());
    }
}
