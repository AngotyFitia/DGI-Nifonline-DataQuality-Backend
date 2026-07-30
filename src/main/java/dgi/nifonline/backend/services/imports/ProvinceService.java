package dgi.nifonline.backend.services.imports;

import dgi.nifonline.backend.dtos.imports.ProvinceDTO;
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

    public void importer(String chemin) throws Exception {
        List<ProvinceDTO> dtos = CSVUtil.lireCSV(chemin);
        for (ProvinceDTO dto : dtos) {
            Province province = new Province();
            province.setIntitule(dto.getIntitule());
            province.setEtat(1); 
            provinceRepository.save(province);
        }
    }
}
