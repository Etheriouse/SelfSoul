public class Entity extends Thing {

    private int x;
    private int y;

    public Entity() {
        super();
    }

    public Entity(String s) {
        super(s);
        this.x = 0;
        this.y = 0;
    }

    public Entity(String s, int x, int y) {
        super(s);
        this.x = x;
        this.y = y;
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


}
