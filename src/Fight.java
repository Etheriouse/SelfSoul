public class Fight {

    Player e1;
    Mob e2;

    public Fight(Player e1, Mob e2) {
        this.e1 = e1;
        this.e2 = e2;
        run();
    }

    private int run() {
        boolean playerAlive = this.e1.hp > 0;
        boolean mobAlive = this.e2.hp > 0;

        while (mobAlive && playerAlive) {

            boolean SelectionAction = true;
            int action = 0;
            while (SelectionAction) {

            }

            switch (action) {
                case 0:
                    e1.getWeapon().apply(e2, e1.getStat()[2], e1.getStat()[3], e1.getStat()[4]);
                    break;

                case 0:
                    e1.getWeapon().apply(e2, e1.getStat()[2], e1.getStat()[3], e1.getStat()[4]);
                    break;

                default:
                    break;
            }

        }

        if (playerAlive) {
            return 0;
        } else {
            return -1;
        }
    }
}
