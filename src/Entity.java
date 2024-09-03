public class Entity extends Thing {

    protected int x;
    protected int y;

    protected int maxHp;
    protected int hp;

    protected String name;

    public Entity() {
        super();
    }

    public Entity(String s) {
        super(s, 0);
        this.x = 0;
        this.y = 0;
    }

    public Entity(String s, int x, int y) {
        super(s, 0);
        this.x = x;
        this.y = y;
    }

    public void move(int x, int y) {
        this.x+=x;
        this.y+=y;
    }

    public void dealDamage(int damage) {
        this.hp-=damage;
    }

    public void dealHealth(int health) {
        this.hp+=health;
        if(this.hp > this.maxHp) {
            this.hp = this.maxHp;
        }
    }



    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getHp() {
        return hp;
    }

    @Override
    public String toString() {
        return this.name+": ("+this.x+", "+this.y+") hp:"+this.hp+"/"+this.maxHp;
    }
}
