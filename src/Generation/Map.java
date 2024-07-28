package Generation;

import java.util.*;


public class Map {

    public static void main(String[] args) {
        Map road = new Map(10, 10);
        road.show();
        System.out.println(("right").compareTo("left"));
        int move = 1;
        System.out.println(move==-1?"right":"left");
    }

    int x, y; // Entry Coordinnate
    int[][] instructionMap; // Construction map
    int[][] map; // Real map

    // public Map(int x, int y) {

    //     Random r = new Random();

    //     this.instructionMap = new int[y][x];
    //     this.map = new int[y*7][x*7];

    //     for(int i = 0; i<y; i++) {
    //         for(int j = 0; j<x; j++) {
    //             this.instructionMap[i][j] = r.nextInt(5);
    //         }
    //     }

    //     for(int i = 0; i<y*7; i++) {
    //         for(int j = 0; j<x*7; j++) {
    //             this.map[i][j] = -1;
    //         }
    //     }

    //     this.x = r.nextInt(x);
    //     this.y = r.nextInt(y);
    // }

    private void pathRoom(int x_, int y_, String direction, int instructionCase, int recurtionlevel) {
        int move = 0;
        if(direction.compareTo("right") == 0 || direction.compareTo("left") == 0) {

            if(direction.compareTo("right") == 0) {
                move = 1;
            } else {
                move = -1;
            }

            switch (instructionCase) {
                case 0:
                    this.apply("null", direction, x_, y_);
                    break;

                case 1:
                    if(x_+move < this.instructionMap[0].length && this.instructionMap[y_][x_+move] != -1) { // check if need to rotate to right on himself
                        this.apply("1x1", direction, x_, y_);
                        this.instructionMap[x_+move][y_] = -1;
                    } else {
                        if(recurtionlevel>4) {
                            pathRoom(x_, y_, direction, 0, 0);
                        } else {
                            pathRoom(x_, y_, move==1?"down":"up", instructionCase, recurtionlevel+1);
                        }
                    }
                    break;

                case 2:

                    break;

                case 3:

                    break;

                case 4:

                    break;

                default:
                    break;
            }

        } else {
            if(direction.compareTo("up") == 0) {
                move = -1;
            } else {
                move = 1;
            }

            switch (instructionCase) {
                case 0:
                    this.apply("null", direction, x_, y_);
                    break;

                case 1:
                    if(y_+move < this.instructionMap.length && this.instructionMap[y_+move][x_] != -1) { // check if need to rotate to right on himself
                        this.apply("1x1", direction, x_, y_);
                    } else {
                        if(recurtionlevel>4) {
                            pathRoom(x_, y_, direction, 0, 0);
                        } else {
                            pathRoom(x_, y_, move==-1?"right":"left", instructionCase, recurtionlevel+1);
                        }
                    }
                    break;

                case 2:

                    break;

                case 3:

                    break;

                case 4:

                    break;

                default:
                    break;
            }
        }

    }

    private void apply(String room, String direction, int x_, int y_) {

    }

    public void show() {
        for (int[] is : instructionMap) {
            for (int i : is) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    class roomInstruction {

        int type;
        int instruction;

        public roomInstruction(int type, int instruction) {
            this.instruction = instruction;
            this.type = type;
        }
    }

    class Room {
        int sizeX, sizeY;
        int type;
        int numberCorridor;
    }

    roomInstruction grid[][];
    BinaryTree<Room> rooms = new BinaryTree<Room>();

    public Map() {

    }

    public Map(int x, int y) {
        Random r = new Random();
        this.grid = new roomInstruction[y][x];

        for(int i = 0; i<y; i++) {
            for(int j = 0; j<x; j++) {
                this.grid[i][j] = new roomInstruction(r.nextInt(5), r.nextInt(3));
            }
        }
    }

    private void walk(int x, int y) {

    }

    /*
     *
     *  instructioncase 1 = un couloir random
     *                  2 = deux couloir random
     *                  0 = aucun couloir salle fermer
     *
     *
     *
     *  4 type de salle:
     *  1x1 2x1 L 2x2
     *
     * each square of instruction room is 7 lenght, so max is 14
     * so the size of real map is x*7 y*7
     *
     * generer le nombre d'entrer entre 1 et 2
     *
     *
     * si case 0:
     *  aucune nouvelle salle
     *
     * si case 1:
     *  cree une salle devant
     *      si null -> a droite
     *      si toute null -> 0 salle cree
     *
     * si case 2:
     *  cree une salle de 2x1 devant,
     *      si une case null obstrue -> on rotatione a droite,
     *      si encore obstruer -> on tourne a droite
     *      si toute obstruer on revient au cas 1
     *
     * si case 3:
     *  cree une salle en L avec un L inverser verticalement et horizontalement
     *      si obstruer -> on rotationne a droite
     *      si encore obstruer -> on tourne a droite
     *      si toute obstrue on revient au cas 2
     *
     * si case 4:                               **
     *  cree une salle en 2x2 axe sur la droite 1*
     *      si obstruer -> decaler a gauche
     *      si encore obstruer -> on tourne a droite
     *      si toute obstruer on revient au cas 0
     */
}
