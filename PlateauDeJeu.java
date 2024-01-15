


public class PlateauDeJeu {

    public static final int JOUEUR_1 = 1; // Constante pour les joueurs
    public static final int JOUEUR_2 = 2;
    // crée un tableau de taille 17x17 et le rempli avec des 0 et des -1.
    // Les 0 représentent les emplacements où les joueurs peuvent se déplacer (case des pions). Les -1 représentent les emplacements où les barrières peuvent être placées. ( cases des barrieres)
    public static int[][] creerEtInitialiserPlateau() {
        int[][] plateau = new int[17][17];

        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                if (i % 2 == 0 && j % 2 == 0) { //represente les cases des pions
                    plateau[i][j] = 0;
                } else {
                    plateau[i][j] = -1; // represente les cases des barrieres
                }
            }
        }
        // place les pions
        plateau[8][0] = JOUEUR_1; // Position initiale du joueur 1
        plateau[8][16] = JOUEUR_2; // Position initiale du joueur 2

        return plateau;
    }

    //Affichage du tableau sur la console.
    public static void afficherPlateau(int[][] plateau) {
        // Affiche les numéros de colonne en haut du plateau.
        System.out.print("     ");
        for (int col = 1; col <= 8; col++) {
            System.out.print("   " + col + "    ");
        }
        System.out.println();

        // Parcourt chaque ligne du plateau pour l'affichage.
        for (int i = 0; i < 17; i++) {
            // Affiche les numéros de ligne sur le côté gauche pour les cases des barrières.
            if (i % 2 != 0) {
                System.out.print((i / 2 + 1) + " ");
            } else {
                System.out.print("  ");
            }

            // Parcourt chaque colonne pour l'affichage.
            for (int j = 0; j < 17; j++) {
                // Pour les colonnes paires affiche les cases du plateau.
                if (j % 2 == 0) {
                    if (plateau[i][j] == 0) {
                        System.out.print("| . |"); // Case vide (case ou les pions se deplace)
                    } else if (plateau[i][j] == 1) {
                        System.out.print("| 1 |"); // Joueur 1 (case du joueur1)
                    } else if (plateau[i][j] == 2) {
                        System.out.print("| 2 |"); // Joueur 2 (case du joueur2)
                    } else if (plateau[i][j] == 3) {
                        System.out.print("| 3 |"); // case pour les barrières
                    } else {
                        System.out.print("|   |"); // Autre valeur tel que -1
                    }
                } else {
                    // Pour les colonnes impaires affiche les emplacements des barrières en verticale
                    if (plateau[i][j] == -1) {
                        System.out.print("   "); // Emplacement pour une barrière en verticale
                    } else if (plateau[i][j] == 3) {
                        System.out.print(" 3 "); // Barrière verticale
                    }
                }
            }
            System.out.println();
        }
    }


}
