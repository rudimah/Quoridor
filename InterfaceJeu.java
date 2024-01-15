import java.util.Scanner;
public class InterfaceJeu {

    //Fonctionnement du jeu sur le terminal.
    public static void main() {
        Scanner scanner = new Scanner(System.in);
        int[][] plateau = PlateauDeJeu.creerEtInitialiserPlateau();
        int[] barrieresRestantes = {10, 10}; // 10 barrières pour chaque joueur
        int[][] donneesJoueurs = {
                {1, 8, 0, 16}, // Joueur 1 commence en (8, 0) et doit atteindre la ligne 16
                {2, 8, 16, 0}  // Joueur 2 commence en (8, 16) et doit atteindre la ligne 0
        };
        int joueurActuel = 1;
        int choix, ligne, colonne, orientation, nbrtour = 1, gagnant = 0;
        boolean saisieValide, jeuencours = true;
        String[] pseudos = new String[2];  //Pseudo du joueur
        System.out.print("Joueur 1, entrez votre pseudo: ");
        pseudos[0] = scanner.nextLine();
        System.out.print("Joueur 2, entrez votre pseudo: ");
        pseudos[1] = scanner.nextLine();

        while (jeuencours) {
            PlateauDeJeu.afficherPlateau(plateau);
            System.out.println(pseudos[joueurActuel - 1] + ", à votre tour." + " vous avez " + barrieresRestantes[joueurActuel - 1] + " barrières restantes." + " Tour N°" + nbrtour);
            System.out.println("Choisissez une action : 1 pour déplacer un pion, 2 pour placer une barrière");
            choix = scanner.nextInt();
            while (choix < 1 || choix > 2) {
                System.out.println("Choix incorrect ! Choisissez une action : 1 pour déplacer un pion, 2 pour placer une barrière");
                choix = scanner.nextInt();
            }
            switch (choix) {
                case 1:
                    do {
                        System.out.println("Déplacement du pion : 1) Haut  2) Bas  3) Droite 4) Gauche ");
                        choix = scanner.nextInt();

                    } while (choix < 0 || choix > 4 || !GestionnaireDeMouvement.verification_pose_pion(donneesJoueurs, plateau, joueurActuel, choix));

                    break;


                case 2:
                    if (barrieresRestantes[joueurActuel - 1] > 0) {
                        do {
                            System.out.print("Entrez le numéro de ligne pour la barrière de (1-8) : ");
                            ligne = scanner.nextInt();
                            System.out.print("Entrez le numéro de la colonne pour la barrière de (1-8) : ");
                            colonne = scanner.nextInt();
                            System.out.print("Orientation de la barrière (1 pour horizontale, 2 pour verticale) : ");
                            orientation = scanner.nextInt();

                            while (ligne < 1 || ligne > 8 || colonne < 1 || colonne > 8) {
                                System.out.println("Coordonnées en dehors des limites. Veuillez réessayer.");
                                System.out.print("Entrez le numéro de ligne pour la barrière de (1-8) :  ");
                                ligne = scanner.nextInt();
                                System.out.print("Entrez le numéro de la colonne pour la barrière de (1-8) : ");
                                colonne = scanner.nextInt();
                                System.out.print("Orientation de la barrière (1 pour horizontale, 2 pour verticale) : ");
                                orientation = scanner.nextInt();
                            }
                            if (GestionnaireBarriere.placerBarriere(plateau, ligne, colonne, orientation, donneesJoueurs)) {
                                GestionnaireBarriere.decrementerBarriereRestante(barrieresRestantes, joueurActuel);
                                saisieValide = true; //verifie si les saisies de l'utilisateur sont correctes.
                            } else {
                                System.out.println("Placement de barrière invalide. vous êtes actuellement entrain de bloquer le seul chemin d'un joueur. Veuillez réessayer.");
                                saisieValide = false;
                            }
                        } while (!saisieValide);
                    } else {
                        System.out.println("Vous n'avez plus de barrières à placer. Veuillez déplacer votre pion.");
                        continue; //permet de rester sur le même joueur s'il ne dispose plus de barrière jusqu'à qu'il déplace son pion.
                    }
                    break;
            }
            if (donneesJoueurs[joueurActuel - 1][2] == donneesJoueurs[joueurActuel - 1][3]) {  // la fin de jeu en prenant la position en colonne du joueur s'il a atteint son objectif
                jeuencours = false; //Permet de savoir si le joueur actuel à gagner ou pas. Sinon la partie continue
                gagnant = joueurActuel;
            }
            nbrtour++;
            joueurActuel = 3 - joueurActuel;

        }
        PlateauDeJeu.afficherPlateau(plateau);
        if (gagnant != 0) { // Vérifiez si un joueur a gagné
            System.out.println(pseudos[gagnant - 1] + " a gagné !");
            System.out.println("Fin de jeu, bien joué !");
        }
    }
}