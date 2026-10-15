package main.presentation;

import main.dao.IDao;
import main.metier.IMetier;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


public class Presentation2 {

    public static void main(String[] args) {
        String[] configFiles = args.length > 0 ? args : new String[]{"config.txt"};

        for (String configFile : configFiles) {
            System.out.println("\n========== Configuration : " + configFile + " ==========");
            try {
                run(configFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void run(String configFile) throws Exception {
        String[] classNames = loadClassNames(configFile);

        IDao dao = (IDao) instantiateClass(classNames[0]);
        System.out.println("[CONTAINER] DAO instancié : " + dao.getClass().getName());

        IMetier metier = (IMetier) instantiateClass(classNames[1]);
        System.out.println("[CONTAINER] Métier instancié : " + metier.getClass().getName());

        injectDependency(metier, "setDao", dao);

        double resultat = metier.calcul();
        System.out.println("Résultats = " + resultat);
    }

    private static String[] loadClassNames(String fileName) throws IOException {
        String[] classNames = new String[2];
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            for (int i = 0; i < 2; i++) {
                String line = reader.readLine();
                if (line == null) {
                    throw new IOException("Le fichier " + fileName + " doit contenir 2 lignes (DAO puis métier)");
                }
                classNames[i] = line.trim();
            }
        }
        return classNames;
    }

    private static Object instantiateClass(String className) throws Exception {
        Class<?> clazz = Class.forName(className);
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        return constructor.newInstance();
    }


    private static void injectDependency(Object target, String setterName, Object dependency) throws Exception {
        for (Method method : target.getClass().getMethods()) {
            if (method.getName().equals(setterName)
                    && method.getParameterCount() == 1
                    && method.getParameterTypes()[0].isAssignableFrom(dependency.getClass())) {
                method.invoke(target, dependency);
                return;
            }
        }
        throw new NoSuchMethodException("Aucun setter " + setterName + " compatible avec " + dependency.getClass().getName());
    }
}
