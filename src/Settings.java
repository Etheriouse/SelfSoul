import java.util.TreeMap;
import javax.swing.ImageIcon;
import java.awt.Image;


public class Settings {

    public static final TreeMap<String, Image> texturesGlobal = new TreeMap<>();

    public static void Setup() {
        texturesGlobal.put("none", getImage("asset/none.png"));
        texturesGlobal.put("black", getImage("asset/black_square.png"));
    }

    private static Image getImage(String s) {
        return new ImageIcon(s).getImage();
    }
}
