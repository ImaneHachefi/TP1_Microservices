package main.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Implémentation qui lit la valeur dans le fichier "valeur.txt".
 */
public class DaoImplFichier implements IDao {
    private static final String FICHIER = "valeur.txt";

    @Override
    public double getValue() {
        System.out.println("[DAO-FICHIER] Lecture de la valeur dans " + FICHIER + "...");
        try (BufferedReader reader = new BufferedReader(new FileReader(FICHIER))) {
            return Double.parseDouble(reader.readLine().trim());
        } catch (IOException | NumberFormatException | NullPointerException e) {
            throw new IllegalStateException("Impossible de lire une valeur valide dans " + FICHIER, e);
        }
    }
}
