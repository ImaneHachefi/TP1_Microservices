package main.dao;

/**
 * Implémentation simulant un appel à un web service distant.
 */
public class DaoImplWS implements IDao {
    @Override
    public double getValue() {
        System.out.println("[DAO-WS] Appel du web service distant (simulé)...");
        return 75.5;
    }
}
