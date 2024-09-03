import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import Generation.Floor;
import Generation.RedimensionnementTableau;

public class Game implements Render {

    // private Thing[][] map;
    private Random random = new Random();
    private Thing[][] floorThing;
    private double fps = 0;
    private long frame = 0L;

    private int posCameraX = 1000;
    private int posCameraY = 1000;
    private Floor floor;

    double playervelocityX = 0.0f;
    double playervelocityY = 0.0f;

    public static final double max_speed_mobs_target = 4.0f;
    public static final double max_speed_mobs = 6.0f;
    public static final double max_speed = 6.0f;
    final double max_speed_sprint = 3.0f;
    double max_speed_actual = max_speed;
    final double acc = 5.0f;

    final double consumtionEnd = 1;
    final double regenEnd = 0.5;

    private Long currentTick = 0L;

    private Long Delta = System.currentTimeMillis();
    private float delta;

    private ArrayList<Mob> mobs = new ArrayList<>();

    private Player player;

    public Game() {

    }

    private boolean colision(int thingType, int x, int y) {
        int x00 = ((x + (int) (Window.Ts * 0.2)) / Window.Ts);
        int y00 = ((y + (int) (Window.Ts * 0.8)) / Window.Ts);

        int x01 = ((x + (int) (Window.Ts * 0.2)) / Window.Ts);
        int y01 = ((y + Window.Ts - 2) / Window.Ts);

        int x10 = ((x + (int) (Window.Ts * 0.8)) / Window.Ts);
        int y10 = ((y + (int) (Window.Ts * 0.8)) / Window.Ts);

        int x11 = ((x + (int) (Window.Ts * 0.8)) / Window.Ts);
        int y11 = ((y + Window.Ts - 2) / Window.Ts);

        return this.floorThing[y00][x00].getType() == thingType ||
                this.floorThing[y01][x01].getType() == thingType ||
                this.floorThing[y10][x10].getType() == thingType ||
                this.floorThing[y11][x11].getType() == thingType;
    }

    private boolean colisionInteraction(int thingType, int x, int y) {
        int x00 = ((player.getX()) / Window.Ts);
        int y00 = ((player.getY()) / Window.Ts);

        int x01 = ((player.getX() + (int) (Window.Ts - 1)) / Window.Ts);
        int y01 = ((player.getY()) / Window.Ts);

        int x10 = ((player.getX()) / Window.Ts);
        int y10 = ((player.getY() + (int) (Window.Ts - 1)) / Window.Ts);

        int x11 = ((player.getX() + (int) (Window.Ts - 1)) / Window.Ts);
        int y11 = ((player.getY() + Window.Ts - 1) / Window.Ts);

        return this.floorThing[y00][x00].getType() == thingType ||
                this.floorThing[y01][x01].getType() == thingType ||
                this.floorThing[y10][x10].getType() == thingType ||
                this.floorThing[y11][x11].getType() == thingType;

    }

    private boolean colisionInteraction(String thing, int x, int y) {
        int x00 = ((player.getX()) / Window.Ts);
        int y00 = ((player.getY()) / Window.Ts);

        int x01 = ((player.getX() + (int) (Window.Ts - 1)) / Window.Ts);
        int y01 = ((player.getY()) / Window.Ts);

        int x10 = ((player.getX()) / Window.Ts);
        int y10 = ((player.getY() + (int) (Window.Ts - 1)) / Window.Ts);

        int x11 = ((player.getX() + (int) (Window.Ts - 1)) / Window.Ts);
        int y11 = ((player.getY() + Window.Ts - 1) / Window.Ts);

        return this.floorThing[y00][x00].getName().equals(thing) ||
                this.floorThing[y01][x01].getName().equals(thing) ||
                this.floorThing[y10][x10].getName().equals(thing) ||
                this.floorThing[y11][x11].getName().equals(thing);

    }

    public void run() {
        this.player = new Player("Liayel", 0, 0, 1);
        this.floor = new Floor(250, 250);
        this.floorThing = new Thing[floor.getHeightFloor()][floor.getWidthFloor()];
        this.floor.RoomsGeneration(20, 40, 40, 5, 500, 250);
        int tableauReduit[][] = RedimensionnementTableau.redimensionnerTableau(this.floor.applyWithoutClean(),
                floor.getWidthFloor() / 4, floor.getHeightFloor() / 4);
        this.floor.apply();

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

        player.move(this.floor.getxEnter() * Window.Ts, this.floor.getyEnter() * Window.Ts);

        this.mobs.add(new Mob("Ghost"));
        this.mobs.get(0).x = this.floor.getxEnter() * Window.Ts;
        this.mobs.get(0).y = (this.floor.getyEnter() + 3) * Window.Ts;

        for (int i = 0; i < this.floorThing.length; i++) {
            for (int j = 0; j < this.floorThing[0].length; j++) {
                this.floorThing[i][j] = convertIntToThing(floor.getFloor()[i][j]);
            }
        }

        Long startExact = System.currentTimeMillis();
        Long start = System.currentTimeMillis();
        Long end;

        int X = this.floorThing[0].length;
        int Y = this.floorThing.length;

        while (true) {
            frame++;

            long now = System.currentTimeMillis();
            delta = (now - Delta) / 10.00f;

            Delta = now;

            if (Window.keysDown.contains(KeyEvent.VK_CONTROL) && this.player.getEd() > 1) {
                if (this.player.getEd() > 1) {
                    this.player.consumEd(this.consumtionEnd * delta);
                }
                this.max_speed_actual = this.max_speed + this.max_speed_sprint;
            } else {
                if (this.player.getEd() < this.player.getMaxEd()) {
                    this.player.regenEd(this.regenEnd * delta);
                }
                this.max_speed_actual = this.max_speed;
            }

            if (Window.keysDown.contains(KeyEvent.VK_D)) {
                playervelocityX += (acc) * delta;
                if (playervelocityX > max_speed_actual) {
                    playervelocityX = max_speed_actual;
                }
            } else if (Window.keysDown.contains(KeyEvent.VK_A)) {
                playervelocityX -= acc * delta;
                if (playervelocityX < -max_speed_actual) {
                    playervelocityX = -max_speed_actual;
                }
            } else {
                if (playervelocityX < 0) {
                    playervelocityX += acc * delta;
                    if (playervelocityX >= 0) {
                        playervelocityX = 0.0f;
                    }
                } else {
                    playervelocityX -= acc * delta;
                    if (playervelocityX <= 0) {
                        playervelocityX = 0.0f;
                    }
                }
            }

            if (Window.keysDown.contains(KeyEvent.VK_S)) {
                playervelocityY += acc * delta;
                if (playervelocityY > max_speed_actual) {
                    playervelocityY = max_speed_actual;
                }
            } else if (Window.keysDown.contains(KeyEvent.VK_W)) {
                playervelocityY -= acc * delta;
                if (playervelocityY < -max_speed_actual) {
                    playervelocityY = -max_speed_actual;
                }
            } else {
                if (playervelocityY < 0) {
                    playervelocityY += acc * delta;
                    if (playervelocityY >= 0) {
                        playervelocityY = 0.0f;
                    }
                } else {
                    playervelocityY -= acc * delta;
                    if (playervelocityY <= 0) {
                        playervelocityY = 0.0f;
                    }
                }
            }

            int moveX = (int) (playervelocityX * delta);
            player.move(moveX, 0);
            if (colision(2, player.getX(), player.getY()) || colision(1, player.getX(), player.getY())) {
                player.move(-moveX, 0);
                // playervelocityX = 0;
            }

            int moveY = (int) (playervelocityY * delta);
            player.move(0, moveY);
            if (colision(2, player.getX(), player.getY()) || colision(1, player.getX(), player.getY())) {
                player.move(0, -moveY);
                // playervelocityY = 0;
            }

            // Window.cls();
            if (Window.Click) {

                int placementX = posCameraX + Window.xMouse - Window.width / 2;
                int placementY = posCameraY + Window.yMouse - Window.height / 2;

                System.out.println(Math.abs(placementX - posCameraX));
                System.out.println(Math.abs(placementY - posCameraY));

                if (Math.abs(placementX - posCameraX) < 2 * Window.Ts
                        && Math.abs(placementY - posCameraY) < 2 * Window.Ts) {
                    if (this.floorThing[placementY / Window.Ts][placementX / Window.Ts].getType() == 2) {
                        openchestScene(placementX / Window.Ts, placementY / Window.Ts);
                    }

                    if (this.floorThing[placementY / Window.Ts][placementX / Window.Ts].getName().equals("box")) {
                        System.out.println(this.floorThing[placementY / Window.Ts][placementX / Window.Ts]);
                        this.floorThing[placementY / Window.Ts][placementX / Window.Ts] = new Thing("broken_box", 3);
                    }

                }
            }

            // System.out.println("delta: " + delta + " velocity: " + playervelocityX + "
            // pos: " + posCameraX);

            if (player.getX() < Window.width / 2) {
                player.setX(Window.width / 2);
            }

            if (player.getY() < Window.height / 2) {
                player.setY(Window.height / 2);
            }

            if (player.getX() + (Window.width / 2) > ((X) * Window.Ts)) {
                player.setX((X * Window.Ts) - (Window.width / 2));
            }

            // System.out.println((Y * 50) - 550);
            if (player.getY() + (Window.height / 2) > (Y * Window.Ts)) {
                player.setY((Y * Window.Ts) - (Window.height / 2));
            }

            posCameraX = player.getX();
            posCameraY = player.getY();

            actualyMob();

            print();
            Window.refresh();

            end = System.currentTimeMillis();
            if (end - start > 1000) {
                fps = (double) (frame * 1000) / (end - startExact);
                start = System.currentTimeMillis();
            }

            if (end - start > 100) {
                currentTick += 1;
            }
        }
    }

    private void openchestScene(int x, int y) {
        boolean chest_open = true;
        Thing chest = this.floorThing[y][x];
        if (!chest.texture.contains("open")) {
            int rarety = 1;
            Thing opennedChest = new Thing("open_" + chest.texture, 2);
            switch (chest.texture) {
                case "ruby_chest":
                    rarety = 4;
                    break;

                case "saphire_chest":
                    rarety = 3;
                    break;

                case "iron_chest":
                    rarety = 2;
                    break;

                case "chest":
                    rarety = 1;
                    break;

                default:
                    rarety = 1;
                    break;
            }
            Item loot[] = this.generateLoot(rarety);

            while (chest_open) {

                long now = System.currentTimeMillis();
                float delta = (now - Delta) / 10.00f;

                Delta = now;

                if (Window.keysDown.contains(KeyEvent.VK_E) || Window.keysDown.contains(KeyEvent.VK_ESCAPE)) {

                    chest_open = false;

                    for (Item item : loot) {
                        if (item != null) {
                            this.player.addItem(item);
                        }
                    }

                }

                print();
                printChest(loot);
                Window.refresh();
            }
            System.out.println(this.player);
            this.floorThing[y][x] = opennedChest;
        }
    }

    private Item[] generateLoot(int rarety) {
        Item loots[] = new Item[random.nextInt(3) + (4 - rarety) + 1];
        String raretyChest;
        switch (rarety) {
            case 1:
                raretyChest = "normal";
                break;

            case 2:
                raretyChest = "iron";
                break;

            case 3:
                raretyChest = "saphire";
                break;

            case 4:
                raretyChest = "ruby";
                break;

            default:
                raretyChest = "normal";
                break;
        }
        for (int i = 0; i < loots.length; i++) {
            loots[i] = Settings.allLoot.get(raretyChest)[random.nextInt(Settings.allLoot.get(raretyChest).length)];
        }
        return loots;
    }

    private Thing convertIntToThing(Integer t) {
        Random r = new Random();
        switch (t) {
            case -1:
                return new Thing("void", 0);

            case 0:
                return new Thing("floor", 3);

            case 1:
                if (r.nextInt(1000) % 2 == 0) {
                    return new Thing("brick1", 1);
                } else {
                    return new Thing("brick2", 1);
                }

            case 2:
                int luck = r.nextInt(100) + 1;
                if (luck < 2) { // 1%
                    return new Thing("ruby_chest", 2);
                } else if (luck < 9) { // 7%
                    return new Thing("saphire_chest", 2);
                } else if (luck < 31) { // 22%
                    return new Thing("iron_chest", 2);
                } else { // 70%
                    return new Thing("chest", 2);
                }

            case 25:
                return new Thing("open_chest", 3);

            case 26:
                return new Thing("open_iron_chest", 3);

            case 27:
                return new Thing("open_saphire_chest", 3);

            case 28:
                return new Thing("open_ruby_chest", 3);

            case 3:
                return new Thing("box", 1);

            case 35:
                return new Thing("broken_box", 3);

            case 4:
                return new Thing("enter", 3);

            case 5:
                return new Thing("next", 3);

            default:
                return new Thing("error", 1);
        }
    }

    private void printCamera(Thing toRenderer[][], int Sx, int Sy, int Ex, int Ey, int X, int Y, int Ts) {

        for (int y = Sy, b = Y / Ts; y < Ey; b += 1, y += Ts) {
            for (int x = Sx, a = X / Ts; x < Ex; a += 1, x += Ts) {
                if (b < toRenderer.length && a < toRenderer[b].length) {
                    if (toRenderer[b][a] != null) {
                        if (toRenderer[b][a].getType() == 2) {
                            Window.drawTexture(x, y, Ts, Ts, Settings.texturesGlobal.get("floor"));
                        }
                        if (toRenderer[b][a].getName().equals("broken_box")) {
                            Window.drawTexture(x, y, Ts, Ts, Settings.texturesGlobal.get("floor"));
                        }
                        Window.drawTexture(x, y, Ts, Ts, toRenderer[b][a].getTexture());
                    }
                }
            }
        }
    }

    private int[][] forDijkstra(Thing floor[][]) {
        int newFloor[][] = new int[floor.length][floor[0].length];
        for (int i = 0; i < floor.length; i++) {
            for (int y = 0; y < floor[0].length; y++) {
                if (floor[i][y].getType() == 3) {
                    newFloor[i][y] = 0;
                } else if(floor[i][y].getType() == 0){
                    newFloor[i][y] = -1;
                } else {
                    newFloor[i][y] = 1;
                }
            }
        }
        return newFloor;
    }

    private void actualyMob() {
        for (Mob mob : mobs) {
            int x, y;

            int localx = player.getX() - Window.width / 2;
            int localy = player.getY() - Window.height / 2;

            x = mob.getX() - localx;
            y = mob.getY() - localy;

            boolean printEntity = x > -Window.width * 2 && y > -Window.height * 2
                    || x > Window.width * 3 && y > Window.height * 3;

            boolean locked = x > Window.width*0.2 && y > Window.height*0.2
                    && x < Window.width*0.8 && y < Window.height*0.8;

            System.out.println("locked: " + locked);
            System.out.println("print entity: " + printEntity);
            if (printEntity) {
                mob.apply(delta, currentTick, this.player, locked, forDijkstra(this.floorThing));

                int moveX = (int) (mob.getXvelocity() * delta);
                mob.move(moveX, 0);
                if (colision(2, mob.getX(), mob.getY()) || colision(1, mob.getX(), mob.getY())) {
                    mob.move(-moveX, 0);
                }

                int moveY = (int) (mob.getYvelocity() * delta);
                mob.move(0, moveY);
                if (colision(2, mob.getX(), mob.getY()) || colision(1, mob.getX(), mob.getY())) {
                    mob.move(0, -moveY);
                }
            }
            Window.cls();
            System.out.println(mob.x);
            System.out.println(mob.y);
        }
    }

    private void printMobs() {
        int i = 0;
        for (Mob mob : mobs) {
            int x, y;

            int localx = posCameraX - Window.width / 2;
            int localy = posCameraY - Window.height / 2;

            x = mob.x - localx;
            y = mob.y - localy;

            boolean printEntity = x > -Window.width / 2 && y > -Window.height / 2
                    || x > Window.width * 1.5 && y > Window.height * 1.5;

            if (printEntity) {
                Window.drawTexture(x, y, Window.Ts, Window.Ts, Settings.texturesGlobal.get(mob.getName()));
                i++;
            }
        }
        // wSystem.out.println("number mob printed: " + i);
    }

    public void printChest(Item loot[]) {
        Window.drawTexture((int) (Window.width * 0.05), (int) (Window.height * 0.05), (int) (Window.width * 0.90),
                (int) (Window.height * 0.90), Settings.texturesGlobal.get("chest_menu"));
        for (int i = 0; i < loot.length; i++) {
            if (i > 3) {
                Window.drawTexture((int) (Window.width * 0.15) + (int) ((i - 4) * Window.width * 0.2),
                        (int) (Window.height * 0.4), 120, 120, Settings.texturesGlobal.get(loot[i].getName()));
            } else {
                Window.drawTexture((int) (Window.width * 0.15) + (int) (i * Window.width * 0.2),
                        (int) (Window.height * 0.2), 120, 120, Settings.texturesGlobal.get(loot[i].getName()));
            }

        }
    }

    public void printBarre(int maxhp, int hp, int maxend, int end) {
        int x = 75;
        int y = 30;
        int width = maxhp;
        int height = 50;
        int heightBar = (int) (height - (height * 0.5));
        int ecart = (int) (height * 0.25);
        int coordooneNohp = x + hp - (int) (height * 0.80);

        Window.drawGradient(x, y, width, heightBar, "#2d173e", "#7d173e");

        if (hp < maxhp - maxhp * 0.05) {
            // Window.drawTexture(x+hp, y-ecart, height, height,
            // Settings.texturesGlobal.get("grad"));
            // Window.drawTexture(coordooneNohp, y-ecart, maxhp-hp-height, height,
            // Settings.texturesGlobal.get("no"));
            Window.drawTexture(x + hp, y - ecart, maxhp - hp, height, Settings.texturesGlobal.get("no"));
            Window.drawTexture(coordooneNohp, y - ecart, height, height, Settings.texturesGlobal.get("grad"));
        }

        Window.drawTexture(x - height, y - ecart, height, height, Settings.texturesGlobal.get("heart"));
        Window.drawTexture(x, y - ecart, width, height, Settings.texturesGlobal.get("barre"));
        Window.drawTexture(x + width - 5, y - ecart, (int) (height / 3.33), height, Settings.texturesGlobal.get("end"));

        int ecartEnd = (height / 4) + (height);

        Window.drawGradient(x, y + ecartEnd, maxend, heightBar, "#2b630d", "#3e678a");

        int coordooneNoend = x + end - (int) (height * 0.80);
        if (end < maxend - maxend * 0.05) {
            // Window.drawTexture(x+hp, y-ecart, height, height,
            // Settings.texturesGlobal.get("grad"));
            // Window.drawTexture(coordooneNohp, y-ecart, maxhp-hp-height, height,
            // Settings.texturesGlobal.get("no"));
            Window.drawTexture(x + end, y - ecart + ecartEnd, maxend - end, height, Settings.texturesGlobal.get("no"));
            Window.drawTexture(coordooneNoend, y - ecart + ecartEnd, height, height,
                    Settings.texturesGlobal.get("grad"));
        }

        Window.drawTexture(x - height, y - ecart + ecartEnd, height, height, Settings.texturesGlobal.get("flame"));
        Window.drawTexture(x, y - ecart + ecartEnd, maxend, height, Settings.texturesGlobal.get("barre"));
        Window.drawTexture(x + maxend - 5, y - ecart + ecartEnd, (int) (height / 3.33), height,
                Settings.texturesGlobal.get("end"));
    }

    public void printItemSlot() {
        int x = (int) (Window.width * 0.02);
        int y = (int) (Window.height * 0.75);
        int xsize = 140;
        int ysize = 210;
        int ecartx = 20;
        int ecarty = 50;
        Window.drawTexture(x, y, xsize, ysize, Settings.texturesGlobal.get("item_slot"));
        Window.drawTexture(x + ecartx + xsize, y, xsize, ysize, Settings.texturesGlobal.get("item_slot"));

        Window.drawTexture(x + ecartx, y + ecarty, 100, 100,
                Settings.texturesGlobal.get(this.player.getHand().getName()));
        Window.drawTexture(x + (ecartx * 2) + xsize, y + ecarty, 100, 100,
                Settings.texturesGlobal.get(this.player.getWeapon().getName()));

    }

    public void printAth() {
        printBarre(this.player.getMaxHp(), this.player.getHp(), (int) (this.player.getMaxEd()),
                (int) (this.player.getEd()));
        printItemSlot();

        int x = (int) (Window.width * 0.85);
        int y = (int) (Window.height * 0.9);
        Window.drawTexture(x, y, 270, 90, Settings.texturesGlobal.get("cadre soul"));
        Window.setColor(Color.white);
        Window.drawTexture(x + 25, y + 20, 50, 50, Settings.texturesGlobal.get("soul"));
        Window.drawString(this.player.getSouls() + "", 30, x + 80, y + 55);
    }

    public void print() {

        Window.nettoyer();

        int X = posCameraX - (Window.width / 2);
        int Tx = X / Window.Ts;
        int Ex = -(X - (Tx * Window.Ts));

        int Y = posCameraY - (Window.height / 2);
        int Ty = Y / Window.Ts;
        int Ey = -(Y - (Ty * Window.Ts));

        printCamera(this.floorThing, Ex, Ey, (-Ex + (Window.width)), (-Ey + (Window.height)), X, Y, Window.Ts);
        printMobs();

        Window.drawTexture(Window.width / 2, (Window.height / 2), Window.Ts, Window.Ts,
                Settings.texturesGlobal.get(this.player.getName()));

        // Window.setColor(Color.WHITE);
        // Window.drawString("fps: " + (double) ((int) (fps * 100) / 100.0), 30, 1200,
        // 40);
        // Window.drawString("x: " + posCameraX + " y: " + posCameraY, 30, 1200, 80);

        // Window.drawString("Velocity: x: " + playervelocityX + " y: " +
        // playervelocityY, 30, 1200, 160);
        printAth();
    }

}
