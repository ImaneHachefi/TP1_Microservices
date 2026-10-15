package main.metier;

import main.dao.IDao;

/**
 * Implémentation qui applique une TVA de 20 % à la valeur du DAO.
 */
public class MetierImplTVA implements IMetier {
    private static final double TAUX_TVA = 0.20;
    private IDao dao;

    public void setDao(IDao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        if (dao == null) {
            throw new IllegalStateException("dao n'a pas été injecté!");
        }
        double res = dao.getValue() * (1 + TAUX_TVA);
        System.out.println("[METIER-TVA] Calcul TTC effectué. Résultat : " + res);
        return res;
    }
}
