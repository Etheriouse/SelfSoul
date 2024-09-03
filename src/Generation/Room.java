package Generation;

public class Room {

    public static int minSize = 12;

    int x, y, width, height, chest;

    public Room(int x, int y, int width, int height, int chest) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.chest = chest;
    }

    public boolean intersects(Room other) {
        return x < other.x + other.width && x + width > other.x &&
               y < other.y + other.height && y + height > other.y;
    }

    public int getCenterX() {
        return x + width / 2;
    }

    public int getCenterY() {
        return y + height / 2;
    }
}