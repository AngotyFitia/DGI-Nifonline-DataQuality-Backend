package dgi.nifonline.backend.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import dgi.nifonline.backend.dtos.imports.ProvinceDTO;

public class CSVUtil {

    public static List<ProvinceDTO> lireCSV(String chemin) throws Exception {
        List<ProvinceDTO> provinces = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(chemin))) {
            String ligne;
            int numeroLigne = 0;
            br.readLine();
            while ((ligne = br.readLine()) != null) {
                numeroLigne++;
                String[] valeurs = ligne.split(",");
                if (valeurs.length < 2) {
                    throw new Exception("Erreur à la ligne " + numeroLigne + " : nombre de colonnes invalide");
                }
                ProvinceDTO dto = new ProvinceDTO(valeurs[0].trim(), valeurs[1].trim());
                dto.validate(numeroLigne);
                provinces.add(dto);
            }
        }
        return provinces;
    }
}
