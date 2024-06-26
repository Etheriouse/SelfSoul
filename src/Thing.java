import java.awt.Image;

public class Thing {

    protected String texture;

    public Thing() {
        this.texture = "none";
    }

    public Thing(String texture) {
        this.texture = texture;
    }

    public Image getTexture() {
        return Settings.texturesGlobal.get(this.texture);
    }

}
