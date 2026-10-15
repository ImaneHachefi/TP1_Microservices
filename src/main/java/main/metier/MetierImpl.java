package main.metier;

import main.dao.IDao;

public class MetierImpl implements IMetier {
    private IDao dao;

    public void setDao(IDao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        if(dao == null) {
           throw new IllegalStateException("dao n'a pas été injecté!");
        }
        double valBase = dao.getValue();
        double res = valBase * 2;
        System.out.println("[METIER] Calcul effectué. Résultat : " + res);
        return res;
    }
}
