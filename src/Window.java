import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.TreeMap;
import java.util.TreeSet;

public class Window extends JFrame {

    public static int width = 1920;
    public static int height = 1080;

    public static int xMouse = 0;
    public static int yMouse = 0;

    public static boolean Click = false;

    public static int Ts = 90; // 90

    private static BufferedImage onscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    private static BufferedImage offscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    private static Graphics2D offscreen = offscreenImage.createGraphics();
    private static Graphics2D onscreen = onscreenImage.createGraphics();

    private static AffineTransform rotation = new AffineTransform();

    private static ImagePanel panel = new ImagePanel(onscreenImage);

    public static TreeSet<Integer> keysDown;
    public static TreeMap<String, Long> cooldown = new TreeMap<>();

    public Window() {

    }

    public void run() {
        Settings.Setup();
        Setup();
        Game g = new Game();
        g.run();
    }

    private void Setup() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1920, 1080);
        this.setContentPane(panel);
        this.setVisible(true);
        Image Cursor = Settings.texturesGlobal.get("cursor");
        Cursor gauntletCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                Cursor, new Point(0, 0), "gauntlet cursor");
        this.setCursor(gauntletCursor);
        keysDown = new TreeSet<Integer>();
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                keysDown.add(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keysDown.remove(e.getKeyCode());
            }
        });
        int MarginTop = this.getInsets().top;
        this.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                xMouse = e.getX();
                yMouse = e.getY() - MarginTop;;
                changeCursor(true);
                Click = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                changeCursor(false);
                Click = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

        });
    }

    private void changeCursor(boolean clicked) {
        if (clicked) {
            Cursor gauntletCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                    new ImageIcon("assets/cursor_clicked.png").getImage(), new Point(0, 0), "gauntlet cursor");
            this.setCursor(gauntletCursor);
        } else {
            Cursor gauntletCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                new ImageIcon("assets/cursor.png").getImage(), new Point(0, 0), "gauntlet cursor");
            this.setCursor(gauntletCursor);
        }
    }

    public static Window GetNewInstance() {
        return new Window();
    }

    public static void refresh() {
        onscreen.drawImage(offscreenImage, 0, 0, null);
        panel.repaint();
        width = panel.getWidth();
        height = panel.getHeight();
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

    public static void drawGradient(int x, int y, int width, int height, String color1, String color2) {
        Color couleur1 = Color.decode(color1);
        Color couleur2 = Color.decode(color2);

        GradientPaint gradient = new GradientPaint(x, y+(height/2), couleur1, x + width, y + (height/2), couleur2);
        offscreen.setPaint(gradient);
        offscreen.fillRect(x, y, width, height);
    }

    public static void setColor(Color c) {
        offscreen.setColor(c);
    }

    public static void cls() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
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