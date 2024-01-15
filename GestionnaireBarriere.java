import java.util.ArrayList;
import java.util.List;

public class GestionnaireBarriere {

    //Permet de placer des barrieres sur un tableau en saissisant des x et y adequates dans les cases impaires libre du tableau.
    public static boolean placerBarriere(int[][] plateau, int x, int y, int orientation, int[][] donneesJoueurs) {
        int indiceX = 2 * (x - 1); //  le tableau est un 17x17 les calculs 2 * (x - 1) et 2 * (y - 1) convertissent ces coordonnées en indices réels du tableau (1-8).
        int indiceY = 2 * (y - 1);

        // Détermination des indices en fonction de l'orientation
        if (orientation == 1) { // Horizontale
            indiceX++; // incrementation en ligne pour bien placer entre les cases des pion
        } else if (orientation == 2) { // Verticale
            indiceY++;
        } else {
            return false; // Orientation non valide
        }

        // Vérification de la validité du placement
        if (!estPlacementValide(plateau, indiceX, indiceY, orientation)) {
            return false;
        }

        // Placement de la barrière sur le plateau
        plateau[indiceX][indiceY] = 3; // marque la position de la barriere
        if (orientation == 1) {
            plateau[indiceX][indiceY + 2] = 3; //horizontal s'étend a droite
        } else {
            plateau[indiceX + 2][indiceY] = 3; // vertical s'étend en bas
        }
        // Vérifier si tous les chemins restent libres après le placement de la barriere
        if (!cheminLibreApresBarriere(plateau, donneesJoueurs)) {
            // Annuler la pose de la barrière et remet la valeur de base à -1
            plateau[indiceX][indiceY] = -1;
            if (orientation == 1) {
                plateau[indiceX][indiceY + 2] = -1;
            } else {
                plateau[indiceX + 2][indiceY] = -1;
            }
            return false;
        }
        // barriere posé avec succès.
        return true;
    }


    // Verification des cas avant de placer sur le tableau. Si x ou y sont hors des limites du plateau retourne false et evite un placement invalide.
    public static boolean estPlacementValide(int[][] plateau, int x, int y, int orientation) {   // Vérifie si les coordonnées de placement x et y sont à l'intérieur des limites du plateau.
        if (x < 0 || x >= plateau.length || y < 0 || y >= plateau[0].length) {
            return false;
        }

        if (plateau[x][y] != -1) { // Vérifie si l'emplacement souhaité est possible de placement. si le plateau à une valeur de -1 cela signifie que l'emplacement est libre.
            return false;
        }

        // Pour un placement horizontale soit 1,  vérifie si la barriere dépasse les limite du tableau. Vu que le placement ce fait par la droite et vérifie aussi si sa droite est déjà occupé.
        if (orientation == 1 && (y + 2 >= plateau[0].length || plateau[x][y + 2] != -1)) {
            return false;
        }
        // Pour un placement  verticale soit 2 vérifie si placer une barrière dépasse les limites du plateau ou l'espace en dessous est deja occupé vu qu'il s'étend en direction du bas
        else if (orientation == 2 && (x + 2 >= plateau.length || plateau[x + 2][y] != -1)) {
            return false;
        }

        // true si tous les test au dessus sont respectés
        return true;
    }


    //Permet de décrementé les barrières des joueurs lors de leurs utilisations.
    public static void decrementerBarriereRestante(int[] barrieresRestantes, int joueur) {
        if (barrieresRestantes[joueur - 1] > 0) { // decremente seulement de 1 les barriere du joueur actuel s'il en dispose.
            barrieresRestantes[joueur - 1]--;
        }
    }

//recupere les données sur le joueur actuel qui joue tel que le numero du joueur sa position en x et y et son objectif a atteidre
    public static boolean cheminLibreApresBarriere(int[][] plateau, int[][] donneesJoueurs) {
        for (int[] joueur : donneesJoueurs) {
            int posXJoueur = joueur[1]; // Position actuelle X du joueur
            int posYJoueur = joueur[2]; // Position actuelle Y du joueur
            int butYJoueur = joueur[3]; // Objectif Y du joueur (son arrivée pour gagner)

            if (!bfsCheminExiste(plateau, posXJoueur, posYJoueur, butYJoueur)) { //Si le joueur a un chemin libre vers son objectif la methode retourne true sinon false
                return false;
            }
        }
        return true;
    }


    //Algorithme qui permet de vérifier si un chemin est disponible jusqu'a son objectif en ayant des positions de départ et d'arrivée
    // et verifie les position avec une liste, Chaque élément de cette liste est un tableau int[] contenant deux valeurs : X et Y.
    public static boolean bfsCheminExiste(int[][] plateau, int posXDepart, int posYDepart, int posYObjectif) {
        boolean[][] dejavisite = new boolean[plateau.length][plateau[0].length]; //Le tableau 'dejavisite' est un tableau qui enregistre les cases déjà visitées.
        List<int[]> fileAttente = new ArrayList<>();
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; //verifie les cases adjacentes a partir de la position actuelle haut, bas, droite, gauche
        fileAttente.add(new int[] {posXDepart, posYDepart}); //une liste qui prend les positions de départ

        while (!fileAttente.isEmpty()) {
            int[] positionActuelle = fileAttente.remove(0); // tant que ce n'est pas vide, elle va retirer les positions et explorer les adjacents depuis cette position
            int posXActuelle = positionActuelle[0]; //extrait les 2 coordonnées X et Y.
            int posYActuelle = positionActuelle[1];

            if (posYActuelle == posYObjectif) { //position egale a l'objectif = chemin trouvé
                return true;
            }

            dejavisite[posXActuelle][posYActuelle] = true; //après avoir verifier la position (si chemin non trouvé) il va placer les positions visités dans le tableau 'dejavisite'

            for (int[] direction : directions) {
                int nouvellePosX = posXActuelle + direction[0] * 2; // La multiplication par 2 est liée à la structure du plateau de jeu. Ce sont les case du pion de la position actuelle à une autre position possible.
                int nouvellePosY = posYActuelle + direction[1] * 2;
                int posXBarriere  = posXActuelle + direction[0]; //représentent les coordonnées de la case des barriere entre 2 cases de pions et regarde si le déplacement est bloqué par une barrière lorsque le pion decide d'aller dans une des case envisagée
                int posYBarriere = posYActuelle + direction[1];

                if (mouvementValide(plateau, dejavisite, nouvellePosX, nouvellePosY, posXBarriere, posYBarriere)) { //cela verifie que le mouvement vers la nouvelle position est possible, si ca l'est il enregistre la nouvelle position et il ajoute a la listepour qu'elle soit explorée.
                    fileAttente.add(new int[] {nouvellePosX, nouvellePosY});
                }
            }
        }
        return false;
    }

    //verifie les limites de tableau, si la position n'a pas déjà été verifier, si la case ou on veut aller est libre et si il n'y a pas de barriere qui fait obstacle
    public static boolean mouvementValide(int[][] plateau, boolean[][] visite, int posX, int posY, int posXBarriere, int posYBarriere) {

        boolean dansLesLimites = posX >= 0 && posX < plateau.length && posY >= 0 && posY < plateau[0].length;
        boolean dejaVisite = visite[posX][posY];
        boolean caseLibre = plateau[posX][posY] == 0;
        boolean cheminBloque = plateau[posXBarriere][posYBarriere] == 3;




        if (!dansLesLimites) { // Vérifier si la position est dans les limites du plateau.
            return false;
        }
        // Vérifier si la position a déjà été visitée.
        if (dejaVisite) {
            return false;
        }
        // Vérifier si la position est libre (pas de pion présent).
        if (!caseLibre) {
            return false;
        }
        // Vérifier si le chemin n'est pas bloqué par une barrière.
        if (cheminBloque) {
            return false;
        }
        // Si toutes les conditions sont remplies, le mouvement est valide.
        return true;
    }

}

