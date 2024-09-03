import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Dijkstra {

    // public static void main(String[] args) {

    // int t[][] = generationTerrain(10, 10);
    // t[1][4] = 7;
    // t[8][6] = 4;
    // int co[];
    // while (t != null) {
    // // int tt[][] = generateDistanceGrid(t, 1, 4);
    // t = move(generateDistanceGrid(t, 1, 4), t);
    // show(t);
    // try {
    // Thread.sleep(500);
    // } catch (Exception e) {

    // }
    // System.out.print("\033[H\033[2J");
    // System.out.flush();

    // }
    // // show(tt);
    // // co = wheretomove(tt, t, 6, 8);
    // // System.out.println("x: " + co[0] + " y: " + co[1]);

    // }

    public static int[] wherePlayer(int xPlayer, int yPlayer, int xMob, int yMob, int matrice[][]) {
        // System.out.println("player is: " + xPlayer + ", " + yPlayer);
        // System.out.println("mob is: " + xMob + ", " + yMob);
        // int matriceDijkstra[][] = generateDistanceGrid(matrice, xPlayer, yPlayer);

        // showBis(resizeMap(matriceDijkstra, 10, xMob, yMob), xPlayer, yPlayer);

        int t[][] = resizeMap(matrice, 10, xMob, yMob, xPlayer, yPlayer);
        int xP = find(t, 4)[0];
        int yP = find(t, 4)[1];
        //showS(t);
        show(generateDistanceGrid(t, xP, yP));
        int xM = find(t, 7)[0];
        int yM = find(t, 7)[1];
        return wheretomove(generateDistanceGrid(t, xP, yP), xM, yM);
    }

    static class Cell {
        int x, y, distance;

        Cell(int x, int y, int distance) {
            this.x = x;
            this.y = y;
            this.distance = distance;
        }
    }

    private static final int[] dX = { -1, 1, 0, 0 };
    private static final int[] dY = { 0, 0, -1, 1 };
    private static final int OBSTACLE = 1;

    /**
     *
     * @param grid   la map
     * @param startX posx de larriver
     * @param startY posy de larriver
     * @return la carte de deplacement
     */
    private static int[][] generateDistanceGrid(int[][] grid, int startX, int startY) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] distances = new int[rows][cols];
        for (int[] row : distances) {
            Arrays.fill(row, 99);
        }
        distances[startY][startX] = 0;

        PriorityQueue<Cell> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(cell -> cell.distance));
        priorityQueue.add(new Cell(startX, startY, 0));

        while (!priorityQueue.isEmpty()) {
            Cell current = priorityQueue.poll();

            for (int i = 0; i < 4; i++) {
                int newX = current.x + dX[i];
                int newY = current.y + dY[i];

                if (isValid(newX, newY, rows, cols) && grid[newY][newX] != -1 && grid[newY][newX] != 1) {
                    int newDist = current.distance + 1; // assuming uniform weight for simplicity
                    if (newDist < distances[newY][newX]) {
                        distances[newY][newX] = newDist;
                        priorityQueue.add(new Cell(newX, newY, newDist));
                    }
                }
            }
        }

        return distances;
    }

    private static boolean isValid(int x, int y, int rows, int cols) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }

    private static int[] wheretomove(int t[][], int x, int y) {
        int deplace_x = 0;
        int deplace_y = 0;
        int min = Integer.MAX_VALUE;
        for (int i = y - 1; i < y + 2; i++) {
            for (int j = x - 1; j < x + 2; j++) {
                if (!(i < 0 || j < 0 || i > t.length - 1 || j > t[i].length - 1)) {
                    // if (map[i][j] == 0) {
                    if (min > t[i][j]) {
                        min = t[i][j];
                        deplace_x = j;
                        deplace_y = i;
                    }
                    // }
                }
            }
        }
        System.out.println("x: " + deplace_x + " y: " + deplace_y);
        return new int[] { deplace_x - x, deplace_y - y };
    }

    private static int[][] move(int t[][], int map[][]) {
        int x = find(map, 4)[1];
        int y = find(map, 4)[0];
        int deplace_x = 0;
        int deplace_y = 0;
        int min = Integer.MAX_VALUE;
        for (int i = y - 1; i < y + 2; i++) {
            for (int j = x - 1; j < x + 2; j++) {
                if (!(i < 0 || j < 0 || i > map.length - 1 || j > map[i].length - 1)) {
                    if (map[i][j] == 0) {
                        if (min > t[i][j]) {
                            min = t[i][j];
                            deplace_x = j;
                            deplace_y = i;
                        }
                    }
                }
            }
            System.out.println();
        }
        System.out.println();

        int temp = map[deplace_y][deplace_x];
        map[deplace_y][deplace_x] = map[y][x];
        map[y][x] = temp;

        return map;
    }

    private static void show(int t[][]) {
        for (int[] is : t) {
            for (int i : is) {
                if (i < 10) {
                    System.out.print(i + "  ");
                } else {
                    if (i == 99) {
                        System.out.print("#  ");
                    } else {
                        System.out.print(i + " ");
                    }
                }
            }
            System.out.println();
        }
    }

    private static void showS(int t[][]) {
        int x = 0, y = 0;
        for (int[] is : t) {
            for (int i : is) {
                switch (i) {
                    case 0:
                        System.out.print("  ");
                        break;

                    case 1:
                        System.out.print("# ");
                        break;

                    case -1:
                        System.out.print(". ");
                        break;

                    case 4:
                        System.out.print("P ");
                        break;

                    case 7:
                        System.out.print("M ");
                        break;

                    default:
                        break;
                }
                x++;
            }
            System.out.println();
            y++;
        }
        System.out.println();
    }

    private static int[][] generationTerrain(int x, int y) {
        int t[][] = {
                { 0, 0, 1, 0, 0, 0, 0, 1, 0, 0 },
                { 0, 0, 1, 0, 7, 0, 0, 1, 0, 0 },
                { 1, 1, 1, 0, 0, 1, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
                { 1, 1, 0, 0, 1, 1, 1, 0, 0, 0 },
                { 0, 1, 0, 0, 1, 0, 1, 1, 1, 0 },
                { 0, 1, 0, 0, 1, 0, 0, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
        };
        return t;
    }

    // Fonctionne sans obstacle, sinon ia debile
    @SuppressWarnings("unused")
    private static int[][] calculateDistance(int t[][]) {
        int x = find(t)[1];
        int y = find(t)[0];
        int tprime[][] = new int[t.length][t[0].length];
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                tprime[i][j] = Math.abs((Math.abs(j - x)) + (Math.abs(i - y)));
            }
        }
        return tprime;
    }

    private static int[] find(int t[][], int... value) {
        int valeur = 7;
        if (value.length != 0) {
            valeur = value[0];
        }
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                if (t[i][j] == valeur) {
                    return new int[] { j, i };
                }
            }
        }
        return new int[] { 0, 0 };
    }

    private static void showBis(int t[][], int xplayer, int yplayer) {
        String to = "";
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                if (t[i][j] > 999) {
                    to = t[i][j] + " ";
                } else if (t[i][j] > 99) {
                    to = t[i][j] + "  ";
                } else if (t[i][j] > 9) {
                    to = t[i][j] + "   ";
                } else {
                    to = t[i][j] + "    ";
                }
                System.out.print(to);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static int[][] resizeMap(int map[][], int size, int x, int y, int xplayer, int yplayer) {
        int newmap[][] = new int[size * 2][size * 2];
        for (int i = 0, a = y - size; a < y + size; a++, i++) {
            for (int j = 0, b = x - size; b < x + size; b++, j++) {
                if (x == b && y == a) {
                    newmap[i][j] = 7;
                } else if (xplayer == b && yplayer == a) {
                    newmap[i][j] = 4;
                } else {
                    newmap[i][j] = map[a][b];
                }
            }
        }
        return newmap;
    }
}
