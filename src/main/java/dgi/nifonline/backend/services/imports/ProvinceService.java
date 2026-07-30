package dgi.nifonline.backend.imports.services;

import dgi.nifonline.backend.dtos.ImportProvinceDTO;
import dgi.nifonline.backend.models.Province;
import dgi.nifonline.backend.repositories.ProvinceRepository;
import dgi.nifonline.backend.utils.CSVUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceService {

    private final ProvinceRepository provinceRepository;

    public ProvinceService(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    public void importer(String chemin) throws Exception {
        List<ImportProvinceDTO> dtos = CSVUtils.lireCSV(chemin);
        for (ImportProvinceDTO dto : dtos) {
            Province province = new Province();
            province.setIntitule(dto.getIntitule());
            province.setEtat(1); // valeur par défaut
            provinceRepository.save(province);
        }
    }
}
