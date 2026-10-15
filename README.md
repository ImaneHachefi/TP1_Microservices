# TP1 – Microservices : Injection de dépendances

Ce TP illustre le principe d'**injection de dépendances** en Java, avec un couplage faible entre les couches grâce aux interfaces. Les classes à utiliser sont décrites dans un fichier de configuration, puis **instanciées dynamiquement par réflexion** et injectées sans aucun `new` codé en dur.

## Architecture

```
TP1-Microservices
├── config.txt                      # Configuration par défaut (DAO puis métier)
├── configs/                        # Configurations prêtes à l'emploi
├── valeur.txt                      # Valeur lue par DaoImplFichier
├── docs/                           # Captures d'écran
└── src/main/java/main
    ├── dao
    │   ├── IDao.java               # Interface DAO
    │   ├── DaoImpl.java            # Valeur fixe (100.0)
    │   ├── DaoImplBD.java          # Simule une base de données (250.0)
    │   ├── DaoImplWS.java          # Simule un web service (75.5)
    │   └── DaoImplFichier.java     # Lit la valeur dans valeur.txt
    ├── metier
    │   ├── IMetier.java            # Interface métier
    │   ├── MetierImpl.java         # valeur × 2
    │   ├── MetierImplTVA.java      # valeur × 1,20
    │   └── MetierImplRemise.java   # valeur × 0,90
    └── presentation
        └── Presentation2.java      # Instanciation dynamique + injection par réflexion
```

## Fonctionnement

1. `Presentation2` lit un fichier de configuration : la **ligne 1** contient la classe DAO, la **ligne 2** la classe métier.
2. Les deux classes sont instanciées avec `Class.forName(...)` et `getDeclaredConstructor().newInstance()`.
3. Le DAO est injecté dans le métier en appelant `setDao(...)` par réflexion.
4. `metier.calcul()` est appelé : changer de comportement ne demande **aucune modification du code**, seulement du fichier de configuration.

Exemple de `config.txt` :

```
main.dao.DaoImplBD
main.metier.MetierImplTVA
```

## Prérequis

- JDK 8 ou supérieur
- Maven (optionnel)

## Compilation et exécution

Depuis la racine du projet (là où se trouve `config.txt`) :

```bash
# Compilation
mvn compile            # ou : javac -encoding UTF-8 -d out $(find src/main -name "*.java")

# Exécution avec config.txt
java -cp target/classes main.presentation.Presentation2

# Exécution avec une ou plusieurs configurations
java -cp target/classes main.presentation.Presentation2 configs/config-bd-tva.txt configs/config-ws-remise.txt
```

> Remplacez `target/classes` par `out` si vous avez compilé avec `javac`.

## Configurations fournies

| Fichier | DAO | Métier | Résultat |
|---|---|---|---|
| `configs/config-defaut.txt` | `DaoImpl` | `MetierImpl` | 200.0 |
| `configs/config-bd-tva.txt` | `DaoImplBD` | `MetierImplTVA` | 300.0 |
| `configs/config-ws-remise.txt` | `DaoImplWS` | `MetierImplRemise` | 67.95 |
| `configs/config-fichier.txt` | `DaoImplFichier` | `MetierImpl` | 84.0 |

## Résultat de l'exécution

### Exécution avec `config.txt`

<img width="1919" height="1022" alt="Capture d&#39;écran 2026-10-15 131303" src="https://github.com/user-attachments/assets/54427a84-7e47-4650-b8d6-382e2238a14e" />

Sortie attendue :

```
========== Configuration : config.txt ==========
[CONTAINER] DAO instancié : main.dao.DaoImplBD
[CONTAINER] Métier instancié : main.metier.MetierImplTVA
[DAO-BD] Récupération de la valeur depuis la base de données (simulée)...
[METIER-TVA] Calcul TTC effectué. Résultat : 300.0
Résultats = 300.0
```

### Structure du projet

<img width="467" height="816" alt="image" src="https://github.com/user-attachments/assets/06af75dc-4d14-40ab-bc39-339d45a9e19c" />

