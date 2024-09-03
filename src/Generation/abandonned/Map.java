package Generation.abandonned;

import java.util.*;

import Generation.Settings;

public class Map {

    static int MAX_HEIGHT = 20;
    static int MIN_ROOM = 10;
    static int MAX_ROOM = 30;
    static int MAX_LENGHT_CORRIDOR = 30;

    BinaryTree<Room> rooms = new BinaryTree<Room>();

    BinaryTree<Room> actual_room;

    public BinaryTree<Room> getRooms() {
        return rooms;
    }

    public void setActual_room(BinaryTree<Room> actual_room) {
        this.actual_room = actual_room;
    }

    public BinaryTree<Room> getActual_room() {
        return actual_room;
    }

    private Integer content[][];

    public Map() {
        int nombreSalle = 0;
        while (!(nombreSalle > MIN_ROOM && nombreSalle < MAX_ROOM)) {
            nombreSalle = walk(this.rooms, 0);
        }
        this.setupStart();
        this.apply(rooms);
        this.actual_room = rooms;
    }

    public Map(int y) {

    }

    private int walk(BinaryTree<Room> root, int height) {
        Random r = new Random();

        int type = r.nextInt(5);
        int instruction = r.nextInt(3);

        if (height > MAX_HEIGHT) {
            root.leave = createNewRoom(type, instruction);
            return 1;
        } else {
            switch (instruction) {
                case 0:
                    root.leave = createNewRoom(type, instruction);
                    return 1;

                case 1:
                    root.leave = createNewRoom(type, instruction);
                    root.left = new BinaryTree<>();
                    return walk(root.left, height + 1) + 1;

                case 2:
                    root.leave = createNewRoom(type, instruction);
                    root.left = new BinaryTree<>();
                    root.right = new BinaryTree<>();
                    return walk(root.left, height + 1) + walk(root.right, height + 1) + 1;

                default:
                    return 0;
            }
        }
    }

    private Room createNewRoom(int type, int numberCorridor) {
        return new Room(type, numberCorridor);
    }

    public void apply(BinaryTree<Room> root_room) {
        // system chuck pas de changement de scene
        // system static changement de scene quand changement de room


        Room room = root_room.leave;
        int SizeX = room.content.length;
        int SizeY = room.content[0].length;
        this.content = new Integer[(MAX_LENGHT_CORRIDOR*2)+SizeY][(MAX_LENGHT_CORRIDOR*2)+SizeX];
        for(int tg = 0; tg<this.content.length; tg++) {
            for(int fdp = 0; fdp<this.content[tg].length; fdp++) {
                this.content[tg][fdp] = 255;
            }
        }
        int height = this.content[0].length;
        int width = this.content.length;
        int centerX = width/2;
        int centerY = height/2;

        int Sy = centerY-(SizeY/2);
        int Sx = centerX-(SizeX/2);

        for(int i = Sy, a=0; i<Sy+SizeY; i++, a++) {
            for(int j = Sx, b=0; j<Sx+SizeX; j++, b++) {
                this.content[i][j] = room.content[a][b];
            }
        }

        int doorNumber[] = {80, 81};

        for(int n = 0; n<room.numberCorridor; n++) {
            if(Settings.findNumberArray2d(this.content, 30).length != 1) {
                this.applyCorridor(Settings.getCorridor("east"), "east", doorNumber[n]);
            } else if(Settings.findNumberArray2d(this.content, 120).length != 1) {
                this.applyCorridor(Settings.getCorridor("north"), "north", doorNumber[n]);
            } else if(Settings.findNumberArray2d(this.content, 60).length != 1) {
                this.applyCorridor(Settings.getCorridor("south"), "south", doorNumber[n]);
            } else if(Settings.findNumberArray2d(this.content, 90).length != 1) {
                this.applyCorridor(Settings.getCorridor("west"), "west", doorNumber[n]);
            }
        }

    }

    public static void main(String[] args) {
        Settings.Setup();
        //BinaryTree<Room> tre = new BinaryTree<Room>(new Room(Settings.square.get(1)[0]));
        //System.out.println(tre.leave.show());
        Map floor = new Map();
        floor.showContent();
        BinaryTree<Room> actualRoom = floor.getActual_room();
        System.out.println("this: ");
        System.out.println(actualRoom);
        floor.setActual_room(actualRoom.left);
        actualRoom = floor.getActual_room();
        floor.apply(actualRoom);
        floor.showContent();
    }

    public void applyCorridor(Integer t[][], String direction, int door) {
        // coordonner x et y du placement du corridor
        int xyc[] = Settings.findNumberArray2d(t, 140);
        if(xyc.length != 2) {
            throw new IllegalArgumentException("invalide Corridor given");
        }
        int Sx =0, Sy = 0, Ex = 0, Ey = 0;

        int xymc[] = new int[1];
        switch (direction) {
            case "west":
                xymc = Settings.findNumberArray2d(this.content, 90);
                if(xymc.length != 2) {
                    throw new IllegalArgumentException("invalide direction and content of corridor given");
                }
                Sx = xymc[1]-t[0].length;
                Sy = xymc[0]-(t.length/2);
                Ex = xymc[1];
                Ey = Sy+t.length;
                break;

            case "north":
                xymc = Settings.findNumberArray2d(this.content, 120);
                if(xymc.length != 2) {
                    throw new IllegalArgumentException("invalide direction and content of corridor given");
                }
                Sx = xymc[1]-(t[0].length/2);
                Sy = xymc[0]-t.length+1;
                Ex = Sx+t[0].length;
                Ey = xymc[0]+1;
                break;

            case "south":
                xymc = Settings.findNumberArray2d(this.content, 60);
                if(xymc.length != 2) {
                    throw new IllegalArgumentException("invalide direction and content of corridor given");
                }
                Sx = xymc[1]-(t[0].length/2);
                Sy = xymc[0];
                Ex = Sx+t[0].length;
                Ey = Sy+t.length;
                break;

            case "east":
                xymc = Settings.findNumberArray2d(this.content, 30);
                if(xymc.length != 2) {
                    throw new IllegalArgumentException("invalide direction and content of corridor given");
                }
                Sx = xymc[1];
                Sy = xymc[0]-(t.length/2);
                Ex = Sx+t[0].length;
                Ey = Sy+t.length;
                break;

            default:
                throw new IllegalArgumentException("invalide direction given");
        }
        for(int i = Sy, a = 0; i<Ey; i++, a++) {
            for(int j = Sx, b = 0; j<Ex; j++, b++) {
                if(t[a][b] == 30 || t[a][b] == 60 ||  t[a][b] == 90 || t[a][b] == 120 || t[a][b] == 140) {
                    this.content[i][j] = 0;
                } else if(t[a][b] == 80) {
                    this.content[i][j] = door;
                } else {
                    this.content[i][j] = t[a][b];
                }
            }
        }

    }

    private void setupStart() {
        Random r = new Random();
        int nbC = this.rooms.leave.numberCorridor;
        this.rooms.leave.content = Settings.start.get(nbC)[r.nextInt(Settings.start.get(nbC).length)];
    }


    public void showContent() {
        for (Integer[] is : this.content) {
            for (int i : is) {
                System.out.print(Settings.convertIntToString(i));
            }
            System.out.println();
        }
        System.out.println();

    }

    public Integer[][] getContent() {
        return this.content;
    }
    /*
     *
     * instructioncase 1 = un couloir random
     * 2 = deux couloir random
     * 0 = aucun couloir salle fermer
     *
     *
     *
     * 4 type de salle:
     * 1x1 2x1 L 2x2
     *
     * each square of instruction room is 7 lenght, so max is 14
     * so the size of real map is x*7 y*7
     *
     * generer le nombre d'entrer entre 1 et 2
     *
     *
     * si case 0:
     * aucune nouvelle salle
     *
     * si case 1:
     * cree une salle devant
     * si null -> a droite
     * si toute null -> 0 salle cree
     *
     * si case 2:
     * cree une salle de 2x1 devant,
     * si une case null obstrue -> on rotatione a droite,
     * si encore obstruer -> on tourne a droite
     * si toute obstruer on revient au cas 1
     *
     * si case 3:
     * cree une salle en L avec un L inverser verticalement et horizontalement
     * si obstruer -> on rotationne a droite
     * si encore obstruer -> on tourne a droite
     * si toute obstrue on revient au cas 2
     *
     * si case 4: **
     * cree une salle en 2x2 axe sur la droite 1*
     * si obstruer -> decaler a gauche
     * si encore obstruer -> on tourne a droite
     * si toute obstruer on revient au cas 0
     */
}
