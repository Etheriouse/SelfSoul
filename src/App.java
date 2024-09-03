import java.util.ArrayList;
import java.util.Random;

public class App {

    public static void main(String[] args) throws Exception {
        //tkt();
        Window.GetNewInstance().run();
    }

    public static void tkt() {
        Random r = new Random();
        int stat[] = new int[4];
        double echantillons = 10000000.0;
        for (int i = 1; i <echantillons ; i++) {
            int luck = r.nextInt(100) + 1;
                if (luck < 2) { // 1%
                    stat[0]+=1;
                } else if (luck < 9) { // 7%
                    stat[1]+=1;
                } else if (luck < 31) { // 22%
                    stat[2]+=1;
                } else { // 70%
                    stat[3]+=1;
                }
        }
        System.out.println("Statistique: ");
        System.out.println("% attendu 1% resulte: " + (double) ((((double) (stat[0]))/echantillons))*100);
        System.out.println("% attendu 7% resulte: " + (double) ((((double) (stat[1]))/echantillons))*100);
        System.out.println("% attendu 22% resulte: " + (double) ((((double) (stat[2]))/echantillons))*100);
        System.out.println("% attendu 70% resulte: " + (double) ((((double) (stat[3]))/echantillons))*100);
    }
}
