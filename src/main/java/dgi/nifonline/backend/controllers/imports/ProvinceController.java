package dgi.nifonline.backend.controllers;

import dgi.nifonline.backend.services.imports.ProvinceService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/import/province")
public class ProvinceController {

    private final ProvinceService provinceImportService;

    public ProvinceController(ProvinceService provinceImportService) {
        this.provinceImportService = provinceImportService;
    }

    @PostMapping
    public String importProvince(@RequestParam("file") MultipartFile file) {
        try {
            File tempFile = File.createTempFile("province", ".csv");
            file.transferTo(tempFile);
            provinceImportService.importer(tempFile.getAbsolutePath());
            return "Import réussi";
        } catch (Exception e) {
            return "Erreur lors de l'import : " + e.getMessage();
        }
    }
}
