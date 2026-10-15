package main.dao;

/**
 * Implémentation simulant une lecture depuis une base de données.
 */
public class DaoImplBD implements IDao {
    @Override
    public double getValue() {
        System.out.println("[DAO-BD] Récupération de la valeur depuis la base de données (simulée)...");
        return 250.0;
    }
}
