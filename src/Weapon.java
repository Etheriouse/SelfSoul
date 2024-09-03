public class Weapon extends Item {

    private int afinityDex; // 1-5
    private int afinityFor; // 1-5
    private int afinityFoi; // 1-5

    public Weapon() {
        this.id = -1;
        this.name = "none";
        this.type = -1;
        this.value = 0;
        this.stack = 1;
    }

    public Weapon(String name, int id, int type, int value, int afinityFor , int afinityDex, int afinityFoi) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.value = value;
        this.afinityDex = afinityDex;
        this.afinityFor = afinityFor;
        this.afinityFoi = afinityFoi;
        this.stack = 1;
    }

    public void apply(Entity e, int frc, int dex, int foi) {
        int damage = this.value;
        int afinityBonus = 0;

        afinityBonus += dex * afinityDex;
        afinityBonus += frc * afinityFor;
        afinityBonus += foi * afinityFoi;

        System.out.println("damage: " + damage);
        System.out.println("bonusaff: " + afinityBonus);

        e.dealDamage(damage + afinityBonus);
    }

    @Override
    public String toString() {
        return super.toString() + " " + convertAff(afinityFor) + "/" + convertAff(afinityDex) + "/" + convertAff(afinityFoi);
    }

    private String convertAff(int i) {
        switch (i) {
            case 0:
                return "-";

            case 1:
                return "D";

            case 2:
                return "C";

            case 3:
                return "B";

            case 4:
                return "A";

            case 5:
                return "S";

            default:
                return "-";
        }
    }
}
