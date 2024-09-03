package Generation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Floor {

    public static void main(String[] args) {
        Floor floor = new Floor(250, 250);
        floor.RoomsGeneration(20, 40, 40, 5, 25, 10);
        floor.apply();
        //floor.showFloor();
    }

    private int widthFloor, heightFloor, enter, next, xEnter, yEnter, box;

    private List<Room> rooms;
    private Random random;

    public Floor(int width, int height) {
        this.widthFloor = width;
        this.heightFloor = height;
        this.rooms = new ArrayList<>();
        this.random = new Random();
    }

    public void RoomsGeneration(int numberRoom, int widthMax, int heightMax, int chestMax, int boxMax, int boxMin) {
        if (Room.minSize > widthMax) {
            throw new IllegalArgumentException("max cannot be smaller than min");
        }
        for (int i = 0; i < numberRoom; i++) {
            int w = random.nextInt(widthMax - Room.minSize) + Room.minSize;
            int h = random.nextInt(heightMax - Room.minSize) + Room.minSize;
            int x = random.nextInt((widthFloor - w) - widthMax) + widthMax;
            if (x + w > widthMax - 20) {
                x -= 10;
            }
            int y = random.nextInt((heightFloor - h) - heightMax) + heightMax;
            if (y + h > heightMax - 20) {
                y -= 10;
            }
            int chest = random.nextInt(chestMax);
            Room newRoom = new Room(x, y, w, h, chest);

            boolean intersect = false;
            for (Room room : rooms) {
                if (newRoom.intersects(room)) {
                    intersect = true;
                    break;
                }
            }

            if (!intersect) {
                rooms.add(newRoom);
            }
        }
        this.box = random.nextInt(boxMax)+boxMin;
        this.enter = random.nextInt(rooms.size());
        this.next = random.nextInt(rooms.size());
        while (this.enter == this.next) {
            this.next = random.nextInt(rooms.size());
        }
    }

    public void WallGeneration() {
        for (Room room : rooms) {
            int numberWall = random.nextInt(room.width + room.height);
            for (int i = 0; i < numberWall; i++) {
                int x = random.nextInt(room.width - 4) + 1;
                int y = random.nextInt(room.height - 4) + 1;

                int type = random.nextInt(4);

                /*
                 * ##.
                 * .#.
                 * .##
                 *
                 * ##.
                 * .#.
                 * #..
                 *
                 * .#.
                 * .#.
                 * ###
                 *
                 * .#.
                 * ###
                 * .#.
                 *
                 * /----------------------/
                 *
                 * ###
                 * ...
                 * .##
                 *
                 * ...
                 * .##
                 * ###
                 *
                 * ###
                 * ..#
                 * ###
                 *
                 * #.#
                 * #.#
                 * #.#
                 */

                if (!intersect(room.x + x, room.y + y)) {
                    switch (type) {
                        case 0:
                            this.floor[room.y + y][room.x + x] = 1;
                            this.floor[room.y + y][room.x + x + 1] = 1;
                            this.floor[room.y + y + 1][room.x + x + 1] = 1;
                            this.floor[room.y + y + 2][room.x + x + 1] = 1;
                            this.floor[room.y + y + 2][room.x + x + 2] = 1;
                            break;

                        case 1:
                            this.floor[room.y + y][room.x + x] = 1;
                            this.floor[room.y + y][room.x + x + 1] = 1;
                            this.floor[room.y + y + 1][room.x + x + 1] = 1;
                            this.floor[room.y + y + 2][room.x + x] = 1;
                            break;

                        case 2:
                            this.floor[room.y + y][room.x + x + 1] = 1;
                            this.floor[room.y + y + 1][room.x + x + 1] = 1;
                            this.floor[room.y + y + 2][room.x + x + 1] = 1;
                            this.floor[room.y + y + 2][room.x + x] = 1;
                            this.floor[room.y + y + 2][room.x + x + 2] = 1;
                            break;

                        case 3:
                            this.floor[room.y + y][room.x + x + 1] = 1;
                            this.floor[room.y + y + 1][room.x + x] = 1;
                            this.floor[room.y + y + 1][room.x + x + 1] = 1;
                            this.floor[room.y + y + 1][room.x + x + 2] = 1;
                            this.floor[room.y + y + 2][room.x + x + 1] = 1;
                            break;

                        case 4:
                            this.floor[room.y + y][room.x + x] = 1;
                            this.floor[room.y + y][room.x + x + 1] = 1;
                            this.floor[room.y + y][room.x + x + 2] = 1;
                            this.floor[room.y + y + 2][room.x + x + 1] = 1;
                            this.floor[room.y + y + 2][room.x + x + 2] = 1;
                            break;

                        case 5:
                            this.floor[room.y + y + 1][room.x + x + 1] = 1;
                            this.floor[room.y + y + 1][room.x + x + 2] = 1;
                            this.floor[room.y + y + 2][room.x + x] = 1;
                            this.floor[room.y + y + 2][room.x + x + 1] = 1;
                            this.floor[room.y + y + 2][room.x + x + 2] = 1;
                            break;

                        case 6:
                            this.floor[room.y + y][room.x + x] = 1;
                            this.floor[room.y + y][room.x + x + 1] = 1;
                            this.floor[room.y + y][room.x + x + 2] = 1;
                            this.floor[room.y + y + 1][room.x + x + 2] = 1;
                            this.floor[room.y + y + 2][room.x + x] = 1;
                            this.floor[room.y + y + 2][room.x + x + 1] = 1;
                            this.floor[room.y + y + 2][room.x + x + 2] = 1;
                            break;

                        case 7:
                            this.floor[room.y + y][room.x + x] = 1;
                            this.floor[room.y + y + 1][room.x + x] = 1;
                            this.floor[room.y + y + 2][room.x + x] = 1;
                            this.floor[room.y + y][room.x + x + 2] = 1;
                            this.floor[room.y + y + 1][room.x + x + 2] = 1;
                            this.floor[room.y + y + 2][room.x + x + 2] = 1;
                            break;

                        default:
                            break;
                    }

                }
            }
        }
    }

    public boolean intersect(int x, int y) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if(this.floor[y+i][x+j] == 4 || this.floor[y+i][x+j] == 5) {
                    return true;
                }
                if (this.floor[y+i][x+j] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public void BoxGeneration() {
        for(int i= 0; i<this.box; i++) {
            boolean spawned = false;
            while (!spawned) {
                int x = random.nextInt(this.widthFloor);
                int y = random.nextInt(this.heightFloor);
                // 2 = chest | 3 = box //
                if (this.floor[y][x] != -1 && this.floor[y][x] != 1 && this.floor[y][x] != 2 && this.floor[y][x] != 4 && this.floor[y][x] != 5){
                    this.floor[y][x] = 3;
                    spawned = true;
                }
            }
        }
    }

    public void CorridorGeneration() {
        for (int i = 0; i < rooms.size() - 1; i++) {
            Room roomA = rooms.get(i);
            Room roomB = rooms.get(i + 1);

            int x1 = roomA.getCenterX();
            int y1 = roomA.getCenterY();
            int x2 = roomB.getCenterX();
            int y2 = roomB.getCenterY();

            while (x1 != x2) {
                if (x1 < x2) {
                    x1++;
                } else {
                    x1--;
                }
                floor[y1 - 2][x1] = 1;
                floor[y1 - 1][x1] = 1;
                floor[y1][x1] = 1;
                floor[y1 + 1][x1] = 1;
                floor[y1 + 2][x1] = 1;
            }

            while (y1 != y2) {
                if (y1 < y2) {
                    y1++;
                } else {
                    y1--;
                }
                floor[y1][x1 - 2] = 1;
                floor[y1][x1 - 1] = 1;
                floor[y1][x1] = 1;
                floor[y1][x1 + 1] = 1;
                floor[y1][x1 + 2] = 1;
            }
        }
    }

    private int[][] floor;

    public void apply() {
        floor = new int[heightFloor][widthFloor];
        for (int i = 0; i < heightFloor; i++) {
            for (int j = 0; j < widthFloor; j++) {
                floor[i][j] = -1;
            }
        }
        CorridorGeneration();

        for (Room room : rooms) {
            for (int i = room.y; i < room.y + room.height; i++) {
                for (int j = room.x; j < room.x + room.width; j++) {
                    floor[i][j] = 1;
                }
            }
        }
        cleanArea();

        int r = 0;
        int n = 0;

        for (Room room : rooms) {
            if (r == this.enter) {
                n = 0;
                while (n < 1) {
                    int x = random.nextInt(room.width - 4) + 2;
                    int y = random.nextInt(room.height - 4) + 2;
                    // 2 = chest | 3 = box //
                    if (floor[y + room.y][x + room.x] != 2 && floor[y + room.y][x + room.x] != 3 && floor[y + room.y][x + room.x] != -1) {
                        floor[y + room.y][x + room.x] = 4;
                        this.xEnter = x + room.x;
                        this.yEnter = y + room.y;
                        n++;
                    }
                }
            } else if (r == this.next) {
                n = 0;
                while (n < 1) {
                    int x = random.nextInt(room.width - 4) + 2;
                    int y = random.nextInt(room.height - 4) + 2;
                    // 2 = chest | 3 = box //
                    if (floor[y + room.y][x + room.x] != 2 && floor[y + room.y][x + room.x] != 3 && floor[y + room.y][x + room.x] != -1) {
                        floor[y + room.y][x + room.x] = 5;
                        n++;
                    }
                }
            }
            r++;
        }
        WallGeneration();

        for (Room room : rooms) {
            n = 0;
            while (n < room.chest) {
                int x = random.nextInt(room.width - 4) + 2;
                int y = random.nextInt(room.height - 4) + 2;

                if (floor[y + room.y][x + room.x] != 2 && floor[y + room.y][x + room.x] != 5 && floor[y + room.y][x + room.x] != 4) {
                    floor[y + room.y][x + room.x] = 2;
                    n++;
                }
            }
        }

        BoxGeneration();

    }

    public int[][] applyWithoutClean() {
        floor = new int[heightFloor][widthFloor];
        for (int i = 0; i < heightFloor; i++) {
            for (int j = 0; j < widthFloor; j++) {
                floor[i][j] = -1;
            }
        }
        CorridorGeneration();

        for (Room room : rooms) {
            for (int i = room.y; i < room.y + room.height; i++) {
                for (int j = room.x; j < room.x + room.width; j++) {
                    floor[i][j] = 1;
                }
            }
        }
        return floor;
    }

    public void showFloor() {

        for (int i = 0; i < heightFloor; i++) {
            for (int j = 0; j < widthFloor; j++) {
                if (floor[i][j] == -1) {
                    System.out.print(". "); // vide
                } else if (floor[i][j] == 1) {
                    System.out.print("# "); // wall
                } else if (floor[i][j] == 2) {
                    System.out.print("@ "); // chest
                } else if (floor[i][j] == 3) {
                    System.out.print("O "); // box
                } else if (floor[i][j] == 4) {
                    System.out.print("[]"); // box
                } else if (floor[i][j] == 5) {
                    System.out.print("{}"); // box
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    private void cleanArea() {
        for (int i = 0; i < heightFloor; i++) {
            for (int j = 0; j < widthFloor; j++) {
                if (i != 0 && i != heightFloor - 1 && j != 0 && j != widthFloor - 1)
                    if (floor[i][j] == 1) {
                        if (!(floor[i - 1][j - 1] == -1 || floor[i - 1][j] == -1 || floor[i - 1][j + 1] == -1 ||
                                floor[i][j - 1] == -1 || floor[i][j + 1] == -1 ||
                                floor[i + 1][j - 1] == -1 || floor[i + 1][j] == -1 || floor[i + 1][j + 1] == -1)) {
                            floor[i][j] = 0;
                        }
                    }
            }
        }
    }

    public int[][] getFloor() {
        return floor;
    }

    public int getWidthFloor() {
        return widthFloor;
    }

    public int getHeightFloor() {
        return heightFloor;
    }

    public int getxEnter() {
        return xEnter;
    }

    public int getyEnter() {
        return yEnter;
    }

}