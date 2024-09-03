
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.TreeMap;

import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Settings {

    public static final TreeMap<String, Image> texturesGlobal = new TreeMap<>();
    public static final TreeMap<String, Item[]> allLoot = getLoots();

    public static void Setup() {
        texturesGlobal.put("none", getImage("assets/none.png"));

        texturesGlobal.put("chest", getImage("assets/chest/chest.png"));
        texturesGlobal.put("open_chest", getImage("assets/chest/chest_open.png"));
        texturesGlobal.put("ruby_chest", getImage("assets/chest/chest_ruby.png"));
        texturesGlobal.put("open_ruby_chest", getImage("assets/chest/chest_ruby_open.png"));
        texturesGlobal.put("saphire_chest", getImage("assets/chest/chest_saphire.png"));
        texturesGlobal.put("open_saphire_chest", getImage("assets/chest/chest_saphire_open.png"));
        texturesGlobal.put("iron_chest", getImage("assets/chest/chest_iron.png"));
        texturesGlobal.put("open_iron_chest", getImage("assets/chest/chest_iron_open.png"));
        texturesGlobal.put("chest_menu", getImage("assets/chest_menu.png"));

        texturesGlobal.put("box", getImage("assets/box/box.png"));
        texturesGlobal.put("broken_box", getImage("assets/box/box_broken.png"));
        texturesGlobal.put("floor", getImage("assets/floor.png"));
        texturesGlobal.put("void", getImage("assets/void.png"));
        texturesGlobal.put("brick1", getImage("assets/brick1.png"));
        texturesGlobal.put("brick2", getImage("assets/brick2.png"));
        texturesGlobal.put("enter", getImage("assets/enter.png"));
        texturesGlobal.put("next", getImage("assets/next.png"));
        texturesGlobal.put("player", getImage("assets/test.png"));
        texturesGlobal.put("cursor", getImage("assets/cursor.png"));
        texturesGlobal.put("cursor_click", getImage("assets/cursor_clicked.png"));

        texturesGlobal.put("Nameless sword", getImage("assets/Item/Weapons/NamelessSword.png"));
        texturesGlobal.put("Blade of Rhea", getImage("assets/Item/Weapons/BladeOfRhea.png"));
        texturesGlobal.put("Spear of Chaos", getImage("assets/Item/Weapons/SpearOfChaos.png"));
        texturesGlobal.put("Stick of the Truth", getImage("assets/Item/Weapons/StickOfTruth.png"));
        texturesGlobal.put("DragonSlayer sword", getImage("assets/Item/Weapons/DragonSlayerSword.png"));
        texturesGlobal.put("Sword", getImage("assets/Item/Weapons/Sword.png"));
        texturesGlobal.put("Spear", getImage("assets/Item/Weapons/Spear.png"));
        texturesGlobal.put("Broken sword", getImage("assets/Item/Weapons/BrokenSword.png"));
        texturesGlobal.put("Stick", getImage("assets/Item/Weapons/Stick.png"));


        texturesGlobal.put("Greatsword", getImage("assets/Item/Weapons/Greatsword.png"));
        texturesGlobal.put("Greatsword of Zodd", getImage("assets/Item/Weapons/GreatswordOfZodd.png"));
        texturesGlobal.put("Magic staff", getImage("assets/Item/Weapons/MagicStaff.png"));
        texturesGlobal.put("Magic staff of Amarlor", getImage("assets/Item/Weapons/MagicStaffOfAmarlor.png"));
        texturesGlobal.put("Niolix's Halberd", getImage("assets/Item/Weapons/Niolix'sHalberd.png"));

        texturesGlobal.put("Soul shard", getImage("assets/Item/soul/shard.png"));
        texturesGlobal.put("Soul part", getImage("assets/Item/soul/part.png"));
        texturesGlobal.put("Soul chunk", getImage("assets/Item/soul/chunk.png"));
        texturesGlobal.put("Soul quarter", getImage("assets/Item/soul/quarter.png"));
        texturesGlobal.put("Soul half", getImage("assets/Item/soul/half.png"));
        texturesGlobal.put("Soul of creature", getImage("assets/Item/soul/creature.png"));
        texturesGlobal.put("Self Soul", getImage("assets/Item/soul/self.png"));



        texturesGlobal.put("heart", getImage("assets/hearthp.png"));
        texturesGlobal.put("flame", getImage("assets/flameend.png"));
        texturesGlobal.put("barre", getImage("assets/hpbarre.png"));
        texturesGlobal.put("grad", getImage("assets/lowhp.png"));
        texturesGlobal.put("no", getImage("assets/nohp.png"));
        texturesGlobal.put("end", getImage("assets/endhpbarre.png"));

        texturesGlobal.put("item_slot", getImage("assets/itemSlot.png"));
        texturesGlobal.put("cadre soul", getImage("assets/soulCadre.png"));
        texturesGlobal.put("soul", getImage("assets/soul.png"));
        texturesGlobal.put("Estus", getImage("assets/Estus.png"));


        texturesGlobal.put("Adventurer", getImage("assets/Entity/Adventurer.png"));
        texturesGlobal.put("Knight", getImage("assets/Entity/Knight.png"));
        texturesGlobal.put("Berserk", getImage("assets/Entity/Berserk.png"));
        texturesGlobal.put("Sorcerer", getImage("assets/Entity/Sorcerer.png"));

        texturesGlobal.put("Ghost", getImage("assets/Entity/Ghost.png"));

        texturesGlobal.put("nothing", getImage("assets/nothing.png"));
    }

    private static TreeMap<String, Item[]> getLoots() {
        return getItems("register/item.txt");
    }

    private static TreeMap<String, Item[]> getItems(String path) {
        LinkedList<String> file = Files.reads(path);

        TreeMap<String, Item[]> items = new TreeMap<>();
        ArrayList<Item> normal = new ArrayList<>();
        ArrayList<Item> iron = new ArrayList<>();
        ArrayList<Item> saphire = new ArrayList<>();
        ArrayList<Item> ruby = new ArrayList<>();

        for (String s : file) {
            String tempString[] = s.split(";");
            if (tempString.length == 8) {
                Weapon temp = new Weapon(tempString[0], Integer.parseInt(tempString[1]),
                        Integer.parseInt(tempString[2]),
                        Integer.parseInt(tempString[3]), Integer.parseInt(tempString[4]),
                        Integer.parseInt(tempString[5]), Integer.parseInt(tempString[6]));
                switch (Integer.parseInt(tempString[7])) {
                    case 1:
                        normal.add(temp);
                        break;

                    case 2:
                        iron.add(temp);
                        break;

                    case 3:
                        saphire.add(temp);
                        break;

                    case 4:
                        ruby.add(temp);
                        break;
                    default:
                        break;
                }
            }
            if (tempString.length == 5) {
                Item temp = new Item(tempString[0], Integer.parseInt(tempString[1]), Integer.parseInt(tempString[2]),
                        Integer.parseInt(tempString[3]));
                switch (Integer.parseInt(tempString[4])) {
                    case 1:
                        normal.add(temp);
                        break;

                    case 2:
                        iron.add(temp);
                        break;

                    case 3:
                        saphire.add(temp);
                        break;

                    case 4:
                        ruby.add(temp);
                        break;
                    default:
                        break;
                }
            }
        }
        items.put("normal", convertArrayListToArray(normal));
        items.put("iron", convertArrayListToArray(iron));
        items.put("saphire", convertArrayListToArray(saphire));
        items.put("ruby", convertArrayListToArray(ruby));
        return items;

    }

    private static Image getImage(String s) {
        return new ImageIcon(s).getImage();
    }

    public static Integer[][] convertImageToInt(String path) {
        Image img = getImage(path);
        Integer map[][] = new Integer[img.getHeight(null)][img.getWidth(null)];

        try {
            BufferedImage image = ImageIO.read(new File(path));

            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    int pixel = image.getRGB(j, i);
                    int red = (pixel >> 16) & 0xFF;
                    int green = (pixel >> 8) & 0xFF;
                    int blue = pixel & 0xFF;
                    map[i][j] = (red + green + blue);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        return map;
    }

    /**
     * Converti un entier i en String de longeur 2 selon les regles des Room
     *
     * @param i l'entier
     * @return l'equivalent en String
     * @note ex: 128 -> '# '
     */
    public static String convertIntToString(int i) {
        String txt = "";
        switch (i) {
            case 200:
                txt += "~ ";
                break;

            case 255:
                txt += "x ";
                break;

            case 0:
                txt += "  ";
                break;

            case 128:
                txt += "# ";
                break;

            case 80:
                txt += "[]";
                break;

            case 81:
                txt += "[]";
                break;

            case 83:
                txt += "[]";
                break;

            case 45:
                txt += "@ ";
                break;

            case 90:
                txt += "<-";
                break;

            case 120:
                txt += "/\\";
                break;

            case 30:
                txt += "->";
                break;

            case 60:
                txt += "\\/";
                break;

            default:
                txt += "&!";
                break;
        }
        return txt;
    }

    /**
     * Rotation du tableau vers la gauche
     *
     * @return un tableau
     */
    public static Integer[][] rotationArrayToLeft(Integer t[][]) {
        return rotationArray(3, t);
    }

    /**
     * Rotation du tableau vers la droite
     *
     * @return un tableau
     */
    public static Integer[][] rotationArrayToRight(Integer t[][]) {
        return rotationArray(1, t);
    }

    /**
     * Rotation du tableau vers la droite number fois
     *
     * @param number le nombre de rotation
     * @return un tableau
     */
    private static Integer[][] rotationArray(int number, Integer x[][]) {
        if (number <= 1) {
            return rotationArray(x);
        } else {
            return rotationArray(number - 1, rotationArray(x));
        }
    }

    /**
     * Rotation du tableau vers a la droite
     *
     * @return le tableau
     */
    private static Integer[][] rotationArray(Integer t[][]) {
        Integer newTab[][] = new Integer[t[0].length][t.length];
        for (int i = 0; i < newTab.length; i++) {
            for (int y = 0; y < newTab[0].length; y++) {
                newTab[i][y] = t[newTab[0].length - y - 1][i];
            }
        }
        return newTab;
    }

    public static int[] findNumberArray2d(Integer t[][], int n) {
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                if (t[i][j] == n) {
                    return new int[] { i, j };
                }
            }
        }
        return new int[] { -1 };
    }

    public static Item[] convertArrayListToArray(ArrayList<Item> bool) {
        Item bol[] = new Item[bool.size()];
        for (int i = 0; i < bol.length; i++) {
            bol[i] = bool.get(i);
        }
        return bol;
    }

    public static int[] getStat(int type) {
        int stat[]; // hea / end / for / dex / foi

        switch (type) {
            case 0: // no class
                stat = new int[] { 10, 10, 10, 10, 10 };
                break;

            case 1: // knight // ekilibred
                stat = new int[] { 12, 10, 11, 9, 8 };
                break;

            case 2: // samourai // dex
                stat = new int[] { 10, 9, 11, 14, 6 };

                break;

            case 3: // berserk // for
                stat = new int[] { 15, 10, 15, 8, 2 };

                break;

            case 4: // sorcerer // foi
                stat = new int[] { 9, 9, 8, 9, 14 };

                break;

            default:
                stat = new int[] { 10, 10, 10, 10, 10 };
                break;
        }
        return stat;
    }

    // TODO cree truc pour exporter en minimum size la mini carte afficher au
    // lancement

}
