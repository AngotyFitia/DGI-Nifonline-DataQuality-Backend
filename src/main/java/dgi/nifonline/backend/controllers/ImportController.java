package dgi.nifonline.backend.controllers;

import dgi.nifonline.backend.dtos.imports.ImportReportDTO;
import dgi.nifonline.backend.dtos.imports.RegimeFiscalDTO;
import dgi.nifonline.backend.services.imports.ProvinceService;
import dgi.nifonline.backend.services.imports.RegionService;
import dgi.nifonline.backend.services.imports.DistrictService;
import dgi.nifonline.backend.services.imports.CommuneService;
import dgi.nifonline.backend.services.imports.SecteurService;
import dgi.nifonline.backend.services.imports.ActiviteService;
import dgi.nifonline.backend.services.imports.RegimeFiscalService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.io.File;

@RestController
@RequestMapping("/import")
public class ImportController {

    private final ProvinceService provinceService;
    private final RegionService regionService;
    private final DistrictService districtService;
    private final CommuneService communeService;
    private final SecteurService secteurService;
    private final ActiviteService activiteService;
    private final RegimeFiscalService regimeFiscalService;

    public ImportController(ProvinceService provinceService, RegionService regionService, DistrictService districtService, CommuneService communeService, SecteurService secteurService, ActiviteService activiteService, RegimeFiscalService regimeFiscalService) {
        this.provinceService = provinceService;
        this.regionService= regionService;
        this.districtService=  districtService;
        this.communeService=  communeService;
        this.secteurService= secteurService;
        this.activiteService= activiteService;
        this.regimeFiscalService= regimeFiscalService;
    }

    @PostMapping("/provinces")
    @PreAuthorize("hasAuthority('administrateur')")
    public ImportReportDTO importProvince(@RequestParam("file") MultipartFile file) {
        try {
            File tempFile = File.createTempFile("province", ".csv");
            file.transferTo(tempFile);

            return provinceService.importer(tempFile.getAbsolutePath());

        } catch (Exception e) {
            return new ImportReportDTO(0, 0, 0, "Erreur lors de l'import : " + e.getMessage());
        }
    }

    @PostMapping("/regions")
    @PreAuthorize("hasAuthority('administrateur')")
    public ImportReportDTO importRegion(@RequestParam("file") MultipartFile file) {
        try {
            File tempFile = File.createTempFile("region", ".csv");
            file.transferTo(tempFile);

            return regionService.importer(tempFile.getAbsolutePath());

        } catch (Exception e) {
            return new ImportReportDTO(0, 0, 0, "Erreur lors de l'import : " + e.getMessage());
        }
    }

    @PostMapping("/districts")
    @PreAuthorize("hasAuthority('administrateur')")
    public ImportReportDTO importDistrict(@RequestParam("file") MultipartFile file) {
        try {
            File tempFile = File.createTempFile("district", ".csv");
            file.transferTo(tempFile);

            return districtService.importer(tempFile.getAbsolutePath());

        } catch (Exception e) {
            return new ImportReportDTO(0, 0, 0, "Erreur lors de l'import : " + e.getMessage());
        }
    }

    @PostMapping("/communes")
    @PreAuthorize("hasAuthority('administrateur')")
    public ImportReportDTO importCommune(@RequestParam("file") MultipartFile file) {
        try {
            File tempFile = File.createTempFile("commune", ".csv");
            file.transferTo(tempFile);

            return communeService.importer(tempFile.getAbsolutePath());

        } catch (Exception e) {
            return new ImportReportDTO(0, 0, 0, "Erreur lors de l'import : " + e.getMessage());
        }
    }

    @PostMapping("/secteurs")
    @PreAuthorize("hasAuthority('administrateur')")
    public ImportReportDTO importSecteur(@RequestParam("file") MultipartFile file) {
        try {
            File tempFile = File.createTempFile("secteur", ".csv");
            file.transferTo(tempFile);

            return secteurService.importer(tempFile.getAbsolutePath());

        } catch (Exception e) {
            return new ImportReportDTO(0, 0, 0, "Erreur lors de l'import : " + e.getMessage());
        }
    }

    @PostMapping("/activites")
    @PreAuthorize("hasAuthority('administrateur')")
    public ImportReportDTO importActivite(@RequestParam("file") MultipartFile file) {
        try {
            File tempFile = File.createTempFile("activite", ".csv");
            file.transferTo(tempFile);

            return activiteService.importer(tempFile.getAbsolutePath());

        } catch (Exception e) {
            return new ImportReportDTO(0, 0, 0, "Erreur lors de l'import : " + e.getMessage());
        }
    }

    @PostMapping("/regimes-fiscaux")
    @PreAuthorize("hasAuthority('administrateur')")
    public ImportReportDTO importRegimesFiscaux(@RequestParam("file") MultipartFile file) {
        try {
            File tempFile = File.createTempFile("regime-fiscal", ".csv");
            file.transferTo(tempFile);

            return regimeFiscalService.importer(tempFile.getAbsolutePath());

        } catch (Exception e) {
            return new ImportReportDTO(0, 0, 0, "Erreur lors de l'import : " + e.getMessage());
        }
    }

    @GetMapping("liste/regimes-fiscaux")
    @PreAuthorize("hasAuthority('administrateur')")
    public Page<RegimeFiscalDTO> getRegimesFiscaux(@RequestParam(defaultValue = "tous") String intitule, @RequestParam(defaultValue = "tous") String description, @RequestParam(defaultValue = "tous") String etat, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return regimeFiscalService.getRegimesFiscaux(intitule, description, etat, pageable);
    }
}
