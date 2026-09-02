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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommuneService {

    private final CommuneRepository communeRepository;
    private final DistrictRepository districtRepository;

    public CommuneService(CommuneRepository communeRepository, DistrictRepository districtRepository) {
        this.communeRepository = communeRepository;
        this.districtRepository = districtRepository;
    }

    @Transactional
    public ImportReportDTO importer(String chemin) throws Exception {
        List<String[]> lignes = CSVUtil.lireCSV(chemin, 3);
        int succes = 0;
        int echec = 0;
        StringBuilder message = new StringBuilder();
        List<Commune> communesToInsert = new ArrayList<>();
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
                    communesToInsert.add(commune);
                    succes++;
                    message.append("Succès: Ligne ").append(lineNumber).append(" → Commune '").append(dto.getIntitule()).append("' insérée avec succès dans le district '").append(district.getIntitule()).append("'.\n");
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
        communeRepository.saveAll(communesToInsert);
        return new ImportReportDTO(lignes.size(), succes, echec, message.toString());
    }

    public Page<CommuneListeDTO> getCommunesAvecFiltres(String province, String region, String district, Pageable pageable) {
        boolean hasProvince = !province.equals("tous");
        boolean hasRegion = !region.equals("tous");
        boolean hasDistrict = !district.equals("tous");

        Page<Commune> communes;
        if (hasProvince && hasRegion && hasDistrict) {
            communes = communeRepository.findByDistrict_Region_Province_IntituleAndDistrict_Region_IntituleAndDistrict_Intitule(province, region, district, pageable);
        } else if (hasProvince && hasRegion) {
            communes = communeRepository.findByDistrict_Region_Province_IntituleAndDistrict_Region_Intitule(province, region, pageable);
        } else if (hasProvince) {
            communes = communeRepository.findByDistrict_Region_Province_Intitule(province, pageable);
        } else {
            communes = communeRepository.findAll(pageable);
        }

        return communes.map(CommuneListeDTO::new);
    }
}