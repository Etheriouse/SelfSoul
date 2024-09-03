public class Estus extends Item {

    private int pointHealed = 15;
    private int base = 150;

    private int level = 1;
    private int maxquantity = 1;
    private int quantity = maxquantity;

    public Estus(int level, int maxquantity) {
        super();
        this.level = level;
        this.maxquantity = maxquantity;
        this.quantity = maxquantity;
    }

    public boolean upEfficiency() {
        if(this.level > 10) {
            return false;
        } else {
            this.level+=1;
            return true;
        }
    }

    public boolean upNumberUse() {
        if(this.maxquantity > 15) {
            return false;
        } else {
            this.maxquantity+=1;
            return true;
        }
    }

    public void resetUse() {
        this.quantity = maxquantity;
    }

    public void apply(Entity e) {
        int value = base + (this.pointHealed*level);
        e.dealHealth(value);
    }

    public int getLevel() {
        return level;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String getName() {
        return "Estus";
    }

    @Override
    public String toString() {
        return "Estus flask" + (this.level==1?"":"+"+ this.level) +": " + this.quantity + " / " + this.maxquantity;
    }

}