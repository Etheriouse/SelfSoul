import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CarreDegrade extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Couleurs pour le dégradé
        Color couleur1 = Color.decode("#2d173e");
        Color couleur2 = Color.decode("#7d173e");

        // Position et taille du carré
        int x = 50;
        int y = 50;
        int tailleX = 1000;
        int tailleY = 50;

        // Créer le dégradé de couleur
        GradientPaint gradient = new GradientPaint(x, y+(tailleY/2), couleur1, x + tailleX, y + (tailleY/2), couleur2);

        // Appliquer le dégradé
        g2d.setPaint(gradient);

        // Dessiner le carré avec le dégradé
        g2d.fillRect(x, y, tailleX, tailleY);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Carré en Dégradé");
        CarreDegrade panel = new CarreDegrade();
        frame.add(panel);
        frame.setSize(1920, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}