package dgi.nifonline.backend.services.imports;

import dgi.nifonline.backend.dtos.imports.CoordonneesDTO;
import dgi.nifonline.backend.dtos.imports.ImportReportDTO;
import dgi.nifonline.backend.models.Coordonnees;
import dgi.nifonline.backend.models.Commune;
import dgi.nifonline.backend.models.District;
import dgi.nifonline.backend.repositories.CoordonneesRepository;
import dgi.nifonline.backend.repositories.CommuneRepository;
import dgi.nifonline.backend.repositories.DistrictRepository;
import dgi.nifonline.backend.utils.CSVUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoordonneesService {

    private final CoordonneesRepository coordonneesRepository;
    private final DistrictRepository districtRepository;
    private final CommuneRepository communeRepository;

    public CoordonneesService(CoordonneesRepository coordonneesRepository, CommuneRepository communeRepository, DistrictRepository districtRepository) {
        this.coordonneesRepository = coordonneesRepository;
        this.communeRepository = communeRepository;
        this.districtRepository = districtRepository;
    }

    public ImportReportDTO importer(String chemin) throws Exception {
        List<String[]> lignes = CSVUtil.lireCSV(chemin, 11);
        int succes = 0;
        int echec = 0;
        StringBuilder message = new StringBuilder();

        int lineNumber = 1;
        for (String[] valeurs : lignes) {
            CoordonneesDTO dto = new CoordonneesDTO(valeurs[0].trim(), valeurs[1].trim(), valeurs[2].trim(), valeurs[3].trim(), valeurs[4].trim(), valeurs[5].trim(), valeurs[6].trim(), Integer.parseInt(valeurs[7]), Double.parseDouble(valeurs[8]), Double.parseDouble(valeurs[9]), valeurs[10]);
            try {
                District district = districtRepository.findByIntitule(dto.getDistrict()).orElse(null);
                Commune commune = communeRepository.findByIntituleAndDistrict(dto.getCommune(), district).orElse(null);
                Coordonnees coordonnees = new Coordonnees();
                coordonnees.setCommune(commune);
                coordonnees.setEmail(dto.getEmail());
                coordonnees.setTelephone(dto.getTelephone());
                coordonnees.setTelephoneSecondaire(dto.getTelephoneSecondaire());
                coordonnees.setSiteWeb(dto.getSiteWeb());
                coordonnees.setAdresse(dto.getAdresse());
                coordonnees.setCodePostal(dto.getCodePostal());
                coordonnees.setLatitude(dto.getLatitude());
                coordonnees.setLongitude(dto.getLongitude());
                if ("Validé".equals(dto.getEtatImport())) {
                    coordonnees.setEtat(1);
                } else if ("En attente".equals(dto.getEtatImport())) {
                    coordonnees.setEtat(0);
                } else {
                    coordonnees.setEtat(-1);
                }

                coordonneesRepository.save(coordonnees);
                succes++;
                message.append("Succès: Ligne ").append(lineNumber).append(" → Coordonnees '").append(dto.getEmail()).append("' insérée avec succès.\n");
            } catch (Exception ex) {
                echec++;
                message.append("Échec: Ligne ").append(lineNumber).append(" → ").append(ex.getMessage()).append("\n");
            }
            lineNumber++;
        }
        return new ImportReportDTO(lignes.size(), succes, echec, message.toString());
    }
}