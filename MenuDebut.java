

import java.util.Scanner;
public class MenuDebut {

    //Menu du début de jeu.
    public static void main() {
        Scanner scan = new Scanner(System.in);
        int choix;

        do{
            System.out.println("Bienvenue dans le jeu Quoridor ! \n Veuillez choisir un choix :\n" + "1 : Lancer \n" + "2 : Règle de jeu \n" + "3 : Quitter"  );
            choix= scan.nextInt();

            switch (choix){
                case 1:
                    // Appelle la fonction qui lance le jeu
                    InterfaceJeu.main();
                    break;
                case 2:
                    // affiche les régles
                    System.out.println( """
                
                IMPORTANT !
                
                Avant de jouer, sachez que les | . | sont des emplacements de case libre pour le déplacement des pions, et que les zones vide eux, sont des emplacements pour les barrières. 
                Les numéros sur le coté gauche et en haut montre les coordonnées pour placer les barrières, ces coordonnées n'ont aucun lien avec les cases des pions.
                
                BUT DU JEU
                Atteindre le premier la ligne opposée à sa ligne de départ.
                
                REGLE POUR 2 JOUEURS
                En début de partie, les barrières sont mis dans leur zone de stockage (10 par joueur).
                Chaque joueur pose son pion au centre de sa ligne de départ.
                
                
                Déroulement d’une partie :
                A tour de rôle, chaque joueur choisit de déplacer son pion ou de poser une de ses barrières.
                Lorsqu’il n’a plus de barrières, un joueur est obligé de déplacer son pion.
                
                Déplacement des pions:
                les pions se déplacent d’une case, horizontalement ou verticalement, en avant ou en arrière
                les barrières doivent être contournées.
                
                Pose des barrières:
                une barrière doit être posée exactement entre 2 blocs de 2 cases.
                La pose des barrières a pour but de se créer son propre chemin ou de ralentir l’adversaire,
                mais il est interdit de lui fermer totalement l’accès à sa ligne de but:
                il faut toujours lui laisser une solution.
                
                Face à face:
                quand les 2 pions se retrouvent en vis-à-vis sur 2 cases voisines non séparées par une barrière,
                le joueur dont c’est le tour peut sauter son adversaire et se placer derrière lui.
                Si une barrière est située derrière le pion sauté, le joueur peut choisir de bifurquer à droite ou à gauche
                du pion sauté.
                
                FIN DE LA PARTIE
                Le premier joueur qui atteint une des 9 cases de la ligne opposée à sa ligne de départ gagne la partie.
                """);
                    break;

                case 3:
                    // Quitter
                    System.out.println("Au revoir");
                    break;
                default:
                    //Message d'erreur
                    System.out.println("Entrez un chiffre entre 1 et 3");
                    break;
            }

        } while (choix <3);
    }
}