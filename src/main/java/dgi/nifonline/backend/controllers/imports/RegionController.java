package dgi.nifonline.backend.controllers;

import dgi.nifonline.backend.dtos.imports.ImportReportDTO;
import dgi.nifonline.backend.services.imports.RegionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/import/region")
public class RegionController {

    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @PostMapping
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
