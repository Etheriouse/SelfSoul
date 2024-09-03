import java.util.Random;

public class Mob extends Entity {

    private double Xvelocity;
    private double Yvelocity;

    private int periode = 75;

    Random random = new Random();

    public Mob(String s) {
        super(s);
        this.periode = random.nextInt(900) + 100;
    }

    public void apply(float delta, Long tick, Player player, boolean in, int matrice[][]) {
        if (!in) {
            if (tick % periode == 0) {
                switch (random.nextInt(4)) {
                    case 0:
                        Xvelocity = -Game.max_speed_mobs;
                        break;

                    case 1:
                        Yvelocity = -Game.max_speed_mobs;
                        break;

                    case 2:
                        Xvelocity = Game.max_speed_mobs;
                        break;

                    case 3:
                        Yvelocity = Game.max_speed_mobs;
                        break;

                    default:
                        break;
                }
                this.Xvelocity *= delta;
                this.Yvelocity *= delta;
            }
        } else {
            int where[] = Dijkstra.wherePlayer(player.getX() / Window.Ts, player.getY() / Window.Ts,
                    this.x / Window.Ts, this.y / Window.Ts, matrice);
            this.Xvelocity = (where[0] * Game.max_speed_mobs_target) * delta;
            this.Yvelocity = (where[1] * Game.max_speed_mobs_target) * delta;

        }
        System.out.println("periode clock: " + periode);
        System.out.println("velocity mob: x: " + Xvelocity + " y: " +  Yvelocity);

    }

    public double getXvelocity() {
        return Xvelocity;
    }

    public double getYvelocity() {
        return Yvelocity;
    }
}
