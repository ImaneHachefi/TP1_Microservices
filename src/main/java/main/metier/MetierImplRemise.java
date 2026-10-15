package main.metier;

import main.dao.IDao;

/**
 * Implémentation qui applique une remise de 10 % à la valeur du DAO.
 */
public class MetierImplRemise implements IMetier {
    private static final double TAUX_REMISE = 0.10;
    private IDao dao;

    public void setDao(IDao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        if (dao == null) {
            throw new IllegalStateException("dao n'a pas été injecté!");
        }
        double res = dao.getValue() * (1 - TAUX_REMISE);
        System.out.println("[METIER-REMISE] Calcul avec remise effectué. Résultat : " + res);
        return res;
    }
}
