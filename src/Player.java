import java.util.ArrayList;
import java.util.TreeMap;
import java.util.function.Predicate;

public class Player extends Entity {

    class Inventory {

        private ArrayList<Item> content;

        public Inventory() {
            this.content = new ArrayList<>();
        }

        public void addItem(Item i) {
            if (i instanceof Weapon) {
                this.content.add(i);
            } else {
                int index = contain(i);
                if (index != -1) {
                    this.content.get(index).upStack(1);
                } else {
                    this.content.add(i);
                }

            }
        }

        public void delItem(Item i) {
            if (i instanceof Weapon) {
                this.content.remove(i);
            } else {
                int index = contain(i);
                if (index != -1) {
                    this.content.get(index).downStack(1);
                    if (this.content.get(index).stack == 0) {
                        this.content.remove(i);
                    }
                }

            }
        }

        public void delItem(int index) {
            this.content.get(index).downStack(1);
            if (this.content.get(index).stack == 0) {
                this.content.remove(index);
            }
        }

        private int contain(Item i) {
            for (int n = 0; n < this.content.size(); n++) {
                if (content.get(n).equals(i)) {
                    return n;
                }
            }
            return -1;
        }

        public ArrayList<Item> Get() {
            return this.content;
        }

        @Override
        public String toString() {
            String inventoryString = "\nInventory: [\n";
            for (Item item : this.content) {
                inventoryString += "    " + item.toString() + " : " + item.stack + ",\n";
            }
            inventoryString += "]";
            return inventoryString;
        }
    }

    private Inventory inventory = new Inventory();
    private Weapon weapon = null;
    private Item hand = null;

    private int[] stat;

    private double maxEd;
    private double ed;

    private int souls = 0;

    public Player(String name, int x, int y, int typeClass) {
        this.name = name;
        this.x = x;
        this.y = y;

        this.stat = Settings.getStat(typeClass);
        this.maxHp = (int) ((Math.log(this.stat[0]) * 174) + 408);
        this.hp = maxHp;
        this.maxEd = this.stat[1] * 6 + 180;
        this.ed = this.maxEd;
        Weapon starter;
        switch (typeClass) {
            /*
             * Spear;8;3;67;1;3;0;2
             * Sword;5;3;78;2;1;0;2
             * Greatsword;10;3;101;2;1;0;2
             * Magic staff;21;3;61;1;0;2;2
             */
            case 1:
                this.texture = "Adventurer";
                starter = new Weapon("Spear", 8, 3, 67, 1, 3, 0);
                break;

            case 2:
                this.texture = "Knight";
                starter = new Weapon("Sword", 5, 3, 78, 2, 1, 0);
                break;

            case 3:
                this.texture = "Berserk";
                starter = new Weapon("Greatsword", 10, 3, 101, 2, 1, 0);
                break;

            case 4:
                this.texture = "Sorcerer";
                starter = new Weapon("Magic staff", 21, 3, 61, 1, 0, 2);
                break;

            default:
                this.texture = "Adventurer";
                starter = new Weapon("Sword", 5, 3, 78, 2, 1, 0);
                break;
        }
        this.weapon = starter;
        this.hand = new Estus(4, 10);
    }

    public void levelup(int addedStat[]) {
        if (this.stat.length == addedStat.length) {
            for (int i = 0; i < this.stat.length; i++) {
                this.stat[i] += addedStat[i];
                if (this.stat[i] > 999) {
                    this.stat[i] = 999;
                }
            }
        }
    }

    public ArrayList<Item> getInventory() {
        return inventory.Get();
    }

    public void addItem(Item i) {
        this.inventory.addItem(i);
    }

    public void deleteItem(Item i) {
        this.inventory.delItem(i);
    }

    public void deleteItem(int i) {
        this.inventory.delItem(i);
    }

    public Weapon getWeapon() {
        return this.weapon == null ? new Weapon("nothing", -1, -1, -1, -1, -1, -1) : this.weapon;
    }

    public Item getHand() {
        return this.hand == null ? new Item("nothing", -1, -1, -1) : this.hand;
    }

    public void setHand(Item hand) {
        this.hand = hand;
    }

    public int[] getStat() {
        return stat;
    }

    public double getEd() {
        return this.ed;
    }

    public void consumEd(double d) {
        this.ed -= d;
    }

    public void regenEd(double d) {
        this.ed += d;
    }

    public double getMaxEd() {
        return this.maxEd;
    }

    public void useEd(int i) {
        this.ed -= i;
        if (this.ed < 0) {
            this.ed = 0;
        }
    }

    public int getSouls() {
        return souls;
    }

    public void addSouls(int souls) {
        this.souls += souls;
    }

    public void remSouls(int souls) {
        this.souls -= souls;
    }

    public int nombreSoulForLevel(int level) {
        return (int) (Math.pow(level, 2) * 7 + 1000);
    }

    @Override
    public String toString() {
        String inventoryString = inventory.toString();
        return super.toString() + "\nVIT:" + this.stat[0] + "\nEND:" + this.stat[1] + "\nFOR:" + this.stat[2] + "\nDEX:"
                + this.stat[3] + "\nFOI:" + this.stat[4] + inventoryString;
    }

}
