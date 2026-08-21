package dgi.nifonline.backend.controllers;

import dgi.nifonline.backend.models.Commune;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.security.access.prepost.PreAuthorize;
import dgi.nifonline.backend.services.imports.CommuneService;
import dgi.nifonline.backend.services.imports.ProvinceService;
import dgi.nifonline.backend.dtos.CommuneListeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import dgi.nifonline.backend.dtos.imports.ProvinceDTO;
import java.util.List;

@RestController
@RequestMapping("/api/territoires")
public class TerritoireController {

    private final CommuneService communeService;
    private final ProvinceService provinceService;

    public TerritoireController(CommuneService communeService, ProvinceService provinceService) {
        this.communeService = communeService;
        this.provinceService = provinceService;
    }

    @GetMapping("/communes")
    @PreAuthorize("hasAuthority('administrateur')")
    public Page<CommuneListeDTO> getCommunes(@RequestParam(defaultValue = "tous") String province, @RequestParam(defaultValue = "tous") String region, @RequestParam(defaultValue = "tous") String district, @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return communeService.getCommunesAvecFiltres(province, region, district, pageable);
    }

    @GetMapping("/provinces")
    @PreAuthorize("hasAuthority('administrateur')")
    public List<ProvinceDTO> getProvinces() {
       return provinceService.getProvinces();
    }
}
