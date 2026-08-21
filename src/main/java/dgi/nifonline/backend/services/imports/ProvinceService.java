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
        List<String[]> lignes = CSVUtil.lireCSV(chemin, 2);

        int succes = 0;
        int echec = 0;
        StringBuilder message = new StringBuilder();

        int lineNumber = 1;
        for (String[] valeurs : lignes) {
            ProvinceDTO dto = new ProvinceDTO(valeurs[0].trim(), valeurs[1].trim());
            try {
                dto.validate(lineNumber);

                if (provinceRepository.findByIntitule(dto.getIntitule()).isPresent()) {
                    echec++;
                    message.append("Échec: Ligne ").append(lineNumber).append(" → Province '").append(dto.getIntitule()).append("' existe déjà.\n");
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
                    message.append("Succès: Ligne ").append(lineNumber).append(" → Province '").append(dto.getIntitule()).append("' insérée avec succès.\n");
                }
            } catch (Exception ex) {
                echec++;
                message.append("Échec: Ligne ").append(lineNumber).append(" → ").append(ex.getMessage()).append("\n");
            }
            lineNumber++;
        }
        return new ImportReportDTO(lignes.size(), succes, echec, message.toString());
    }

    public List<ProvinceDTO> getProvinces() {
        return provinceRepository.findAll().stream().map(p -> new ProvinceDTO(p.getIntitule(), String.valueOf(p.getEtat()))).toList();
    }
}