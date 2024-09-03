package Generation;

import java.util.Random;

public class algo {

    public static void main(String[] args) {
        algo n = new algo();
        while (number < 100) {
            n = new algo();
        }
        //n.showRoom();
    }

    private int[][] rooms;
    private int[][] road;
    private static int road_height = 1000;
    private static int road_width = 1000;
    static int number = 0;

    public algo() {
        this.road = new int[road_height][road_width];
        this.rooms = new int[road_height][road_width];

        Random r = new Random();

        for (int i = 0; i < road_height; i++) {
            for (int j = 0; j < road_width; j++) {
                this.road[i][j] = r.nextInt(1000);
                this.rooms[i][j] = -1;
            }
        }

        number = walk(road_width / 2, road_height / 2);
        //System.out.println(number);
    }

    private int walk(int x, int y) {
        int t = this.road[y][x];
        this.road[y][x] = -1;
        if (x == road_width - 1 || x == 0 || y == road_height - 1 || y == 0) {
            this.rooms[y][x] = 0;
            return 1;
        } else {
            if(t == 0) {
                this.rooms[y][x] = 0;
                return 1;
            } else if(t > 0 && t < 250) {
                this.rooms[y][x] = 1;
                return 1 + walk(x + 1, y);
            } else if(t > 250 && t < 500) {
                this.rooms[y][x] = 2;
                return 1 + walk(x - 1, y);
            } else if(t > 500 && t < 750) {
                this.rooms[y][x] = 3;
                return 1 + walk(x, y + 1);
            } else if(t > 750 && t < 1000) {
                this.rooms[y][x] = 4;
                return 1 + walk(x, y - 1);
            } else {
                return 0;
            }
        }
    }

    public void showRoom() {
        for (int[] mapflags : rooms) {
            for (int mapflag : mapflags) {
                if (mapflag == -1) {
                    System.out.print("* ");
                } else {
                    System.out.print("# ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

}
