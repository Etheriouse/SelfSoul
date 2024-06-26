public class Game implements Render{


    private Thing[][] map;
    private double fps = 0;
    private long frame = 0L;

    public Game() {

    }

    public void run() {
        Entity t = new Entity("player");
        t.getTexture();
        Long startExact = System.currentTimeMillis();
        Long start = System.currentTimeMillis();
        Long end;
        while (true) {
            frame++;
            print();
            Window.refresh();

            end = System.currentTimeMillis();
            if(end-start>1000) {
                fps = (double) (frame*1000) / (end-startExact);
                start = System.currentTimeMillis();
            }
        }
    }

    public void print() {
        for(int i = 0; i<27; i++) {
            for(int y = 0; y<48; y++) {
                Window.drawTexture(y*40, i*40, 40, 40, 0, Settings.texturesGlobal.get("black"));
            }
        }

        Window.drawString("fps: " + (double) ((int) (fps*100) /100.0), 30, 0, 40);
    }
}
