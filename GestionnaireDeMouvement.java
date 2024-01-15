
import java.util.Scanner;

public class GestionnaireDeMouvement {

    public static boolean vide(int[][] plateau, int x, int y) {
        //Retourne vrai si la postion n'est pas vide
        return (plateau[x][y] == 0);
    }

    public static boolean verification_pose_pion(int[][] donneesJoueurs, int[][] plateau, int tour, int choix) {

        boolean poser = false;
        Scanner scan = new Scanner(System.in);
        // initialisation des coordonnées
        int nv_postion_joueur_x = donneesJoueurs[tour - 1][1];
        int nv_postion_joueur_y = donneesJoueurs[tour - 1][2];
        int position_suivante_x = donneesJoueurs[tour - 1][1];
        int position_suivante_y = donneesJoueurs[tour - 1][1];
        int position_barriere_x = donneesJoueurs[tour - 1][1];
        int position_barriere_y = donneesJoueurs[tour - 1][2];
        int position_barriere_suivante_x = donneesJoueurs[tour - 1][1];
        int position_barriere_suivante_y = donneesJoueurs[tour - 1][2];


        switch (choix) {
            // Mis à jour des coordonnées en fonction du choix
            case 1:
                // Haut
                nv_postion_joueur_x -= 2;
                position_suivante_x = nv_postion_joueur_x - 2;
                position_barriere_x -= 1;
                position_barriere_suivante_x = position_barriere_x - 2;
                break;
            case 2:
                // Bas
                nv_postion_joueur_x += 2;
                position_suivante_x = nv_postion_joueur_x + 2;
                position_barriere_x += 1;
                position_barriere_suivante_x = position_barriere_x + 2;
                break;
            case 3:
                // Droite
                nv_postion_joueur_y += 2;
                position_suivante_y = nv_postion_joueur_y + 2;
                position_barriere_y += 1;
                position_barriere_suivante_y = position_barriere_y + 2;
                break;
            case 4:
                // Gauche
                nv_postion_joueur_y -= 2;
                position_suivante_y = nv_postion_joueur_y - 2;
                position_barriere_y -= 1;
                position_barriere_suivante_y = position_barriere_y - 2;
                break;
            default:
                System.out.println("Saisiez un chiifre entre 1 et 4");
        }
        if (nv_postion_joueur_x > 16 || nv_postion_joueur_x < 0 || nv_postion_joueur_y > 16 || nv_postion_joueur_y < 0) {
            // Si les postions dépasse la limite
            System.out.println("Vous sortez du Plateau");

        } else if (plateau[position_barriere_x][position_barriere_y] == 3) {
            // Si il y a une barrière
            System.out.println("Une barrière à été détecté ");

        } else if (vide(plateau, nv_postion_joueur_x, nv_postion_joueur_y)) {
            // Si la position est vide

            poser_pion(donneesJoueurs, plateau, tour, nv_postion_joueur_x, nv_postion_joueur_y);
            poser = true;
        } else {
            // Dans le cas où y a un pion

            if (position_suivante_x > 16 || position_suivante_x < 0 || position_suivante_y > 16 || position_suivante_y < 0) {
                // Si les postions dépasse la limite
                System.out.println("Vous sortez du Plateau");
            } else if (plateau[position_barriere_suivante_x][position_barriere_suivante_y] == 3) {
                // Si il y a une barrière
                do {
                    // Puisque il y a une barrière le jouers a le choix entre aller en haut ou en bas
                    System.out.println("Une barrière vous opoose ou voulez vous aller 1: Haut 2: Bas");
                    choix = scan.nextInt();

                    switch (choix) {
                        case 1:
                            // Haut
                            poser_pion(donneesJoueurs, plateau, tour, nv_postion_joueur_x-2, nv_postion_joueur_y);
                            break;
                        case 2:
                            // Bas
                            poser_pion(donneesJoueurs, plateau, tour, position_suivante_x + 2, nv_postion_joueur_y);
                            break;
                        default:
                            System.out.println("Entrer un chiffre entre 1 et 2");
                    }
                    poser = true;
                } while (choix < 0 || choix > 2);
            } else if (vide(plateau, position_suivante_x, position_suivante_y)) { // Et que la place d'après est vide

                poser_pion(donneesJoueurs, plateau, tour, position_suivante_x, position_suivante_y);
                poser = true;
            }

        }

        return poser;
    }

    public static void poser_pion(int[][] donneesJoueurs, int[][] plateau, int tour, int postion_joueur_x, int postion_joueur_y) {
        // déplace le pion tout en mettant à jour les nouvelles coordonnées
        plateau[donneesJoueurs[tour - 1][1]][donneesJoueurs[tour - 1][2]] = 0;
        plateau[postion_joueur_x][postion_joueur_y] = tour;
        donneesJoueurs[tour - 1][1] = postion_joueur_x;
        donneesJoueurs[tour - 1][2] = postion_joueur_y;
    }
}