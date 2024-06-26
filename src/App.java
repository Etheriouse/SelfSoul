public class App {
    public static void main(String[] args) throws Exception {
        tkt();
        Window.GetNewInstance().run();
    }

    public static void tkt() {
        int x = 1920;
        int y = 1080;
        for(int i = 1; i<500; i++) {
            if(x%i == 0 && y%i == 0) {
                System.out.println(i);
            }
        }
    }
}
