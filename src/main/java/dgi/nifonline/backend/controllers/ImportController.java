package dgi.nifonline.backend.controllers;

import dgi.nifonline.backend.dtos.imports.ImportReportDTO;
import dgi.nifonline.backend.services.imports.ProvinceService;
import dgi.nifonline.backend.services.imports.RegionService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.File;

@RestController
@RequestMapping("/import")
public class ImportController {

    private final ProvinceService provinceService;
    private final RegionService regionService;

    public ImportController(ProvinceService provinceService, RegionService regionService) {
        this.provinceService = provinceService;
        this.regionService= regionService;
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
}
