public class Item {

    protected String name;
    protected int id;
    protected int type;
    protected int value;
    protected int stack;

    public Item() {
        this.id = -1;
        this.name = "none";
        this.type = -1;
        this.value = 0;
        this.stack = 1;
    }

    public Item(String name, int id, int type, int value) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.value = value;
        this.stack = 1;
    }

    public void apply(Entity s) {
        switch (this.type) {
            case 0: // buff health
                s.dealHealth(this.value);
                break;

            case 1: // buff atk / def
                // TODO jsp mdr
                break;

            case 2: // atk
                s.dealDamage(this.value);
                break;

            case 4: // up numberofsoul
                if(s instanceof Player) {
                    ((Player)s).addSouls(this.value);
                }

            default: // type 3 weapons
                break;
        }
    }

    public void downStack(int i) {
        this.stack-=i;
    }

    public void upStack(int i) {
        this.stack+=i;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return this.name+" "+this.id+"/"+this.type+"/"+this.value;
    }

    @Override
    public boolean equals(Object i) {
        if(i instanceof Item) {
            return ((Item)i).getId() == this.id;
        } else {
            return false;
        }
    }
}
