import java.awt.Image;

public class Thing {

    protected String texture;
    protected int type;

    public Thing() {
        this.texture = "none";
    }

    /**
     *
     * @param texture
     * @param type
     * @note list of type: Other => 0 Obstacle => 1 Chest => 2 Floor => 3
     */
    public Thing(String texture, int type) {
        this.texture = texture;
        this.type = type;
    }

    public Image getTexture() {
        return Settings.texturesGlobal.get(this.texture);
    }

    public String getName() {
        return this.texture;
    }

    public int getType() {
        return this.type;
    }

}
