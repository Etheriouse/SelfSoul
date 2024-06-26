import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Window extends JFrame {

    public static int width = 1920;
    public static int height = 1080;

    private static BufferedImage onscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    private static BufferedImage offscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    private static Graphics2D offscreen = offscreenImage.createGraphics();
    private static Graphics2D onscreen = onscreenImage.createGraphics();

    private static AffineTransform rotation = new AffineTransform();

    private static ImagePanel panel = new ImagePanel(onscreenImage);

    public Window() {

    }

    public void run() {
        Setup();
        Settings.Setup();
        Game g = new Game();
        g.run();
    }

    private void Setup() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1920, 1080);
        this.setContentPane(panel);
        this.setVisible(true);
    }

    public static Window GetNewInstance() {
        return new Window();
    }

    public static void refresh() {
        onscreen.drawImage(offscreenImage, 0, 0, null);
        panel.repaint();
    }

    public static void nettoyer() {
        offscreen.setColor(Color.BLACK);
        offscreen.fillRect(0, 0, width, height);
    }

    public static void drawString(String s, int size, int x, int y) {
        offscreen.setFont(new Font("Arial", 0, size));
        offscreen.drawString(s, x, y);
    }

    public static void drawTexture(int x, int y, int sizeX, int sizeY, Image texture) {
        offscreen.drawImage(texture, x, y, sizeX, sizeY, null);
    }

    public static void drawTexture(int x, int y, int sizeX, int sizeY, int angle, Image texture) {
        rotation.rotate(Math.toRadians(angle), x+(sizeX/2), y+(sizeY/2));
        offscreen.setTransform(rotation);
        offscreen.drawImage(texture, x, y, sizeX, sizeY, null);
        rotation.rotate(Math.toRadians(-angle), x+(sizeX/2), y+(sizeY/2));
        offscreen.setTransform(rotation);
    }
}

class ImagePanel extends JComponent {
    private Image image;

    public ImagePanel(Image image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

    public void setImage(Image i) {
        this.image = i;
    }
}