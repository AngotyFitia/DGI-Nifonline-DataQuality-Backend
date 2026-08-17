package dgi.nifonline.backend.utils;

import com.opencsv.CSVReader;   
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {
    public static List<String[]> lireCSV(String chemin, int expectedColumns) throws Exception {
        List<String[]> lignes = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(chemin))) {
            String[] valeurs;
            int numeroLigne = 0;
            reader.readNext();
            while ((valeurs = reader.readNext()) != null) {
                numeroLigne++;
                if (valeurs.length != expectedColumns) {
                    throw new Exception("Erreur à la ligne " + numeroLigne +
                        " : nombre de colonnes invalide (attendu " + expectedColumns + ")");
                }
                lignes.add(valeurs);
            }
        }
        return lignes;
    }
}
