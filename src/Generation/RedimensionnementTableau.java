package Generation;

import java.util.Arrays;

public class RedimensionnementTableau {
    public static void main(String[] args) {
        Floor f = new Floor(200, 200);
        f.RoomsGeneration(15, 40, 40, 5, 15);
        f.apply();
        f.showFloor();

        int[][] tableau = f.applyWithoutClean();

        int nouvelleLargeur = f.getWidthFloor()/4;
        int nouvelleHauteur = f.getHeightFloor()/4;

        int[][] tableauReduit = redimensionnerTableau(tableau, nouvelleLargeur, nouvelleHauteur);

        for (int i = 0; i < tableauReduit.length; i++) {
            for (int j = 0; j < tableauReduit[0].length; j++) {
                if (tableauReduit[i][j] == -1) {
                    System.out.print(". "); // vide
                } else if (tableauReduit[i][j] == 1) {
                    System.out.print("# "); // wall
                } else if (tableauReduit[i][j] == 2) {
                    System.out.print("@ "); // chest
                } else if (tableauReduit[i][j] == 3) {
                    System.out.print("O "); // box
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    public static int[][] redimensionnerTableau(int[][] original, int nouvelleLargeur, int nouvelleHauteur) {
        int largeurOriginale = original.length;
        int hauteurOriginale = original[0].length;

        int[][] reduit = new int[nouvelleLargeur][nouvelleHauteur];

        double rapportLargeur = (double) largeurOriginale / nouvelleLargeur;
        double rapportHauteur = (double) hauteurOriginale / nouvelleHauteur;

        for (int i = 0; i < nouvelleLargeur; i++) {
            for (int j = 0; j < nouvelleHauteur; j++) {
                int xOrigine = (int) (i * rapportLargeur);
                int yOrigine = (int) (j * rapportHauteur);

                reduit[i][j] = original[xOrigine][yOrigine];
            }
        }

        return reduit;
    }
}