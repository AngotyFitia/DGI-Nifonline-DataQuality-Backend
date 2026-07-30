package dgi.nifonline.backend.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {

    public static List<String[]> lireCSV(String chemin, int expectedColumns) throws Exception {
        List<String[]> lignes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(chemin))) {
            String ligne;
            int numeroLigne = 0;
            br.readLine();
            while ((ligne = br.readLine()) != null) {
                numeroLigne++;
                String[] valeurs = ligne.split(",");
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
