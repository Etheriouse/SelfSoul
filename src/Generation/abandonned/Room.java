package Generation.abandonned;

import java.util.*;

import Generation.Settings;

public class Room {

    int type;
    int numberCorridor;
    Integer content[][];

    public Room(int type, int numberCorridor) {
        Random r = new Random();
        this.type = type;
        switch (type) {
            case 0:
                this.content = Settings.square.get(numberCorridor)[r.nextInt(Settings.square.get(numberCorridor).length)];
                break;

           /*case 1:
                this.content = Settings.L[r.nextInt(Settings.L.length)];
                break;

            case 2:
                this.content = Settings.T[r.nextInt(Settings.T.length)];
                break;

            case 3:
                this.content = Settings.O[r.nextInt(Settings.O.length)];
                break;
*/
            default:
                this.content = Settings.square.get(numberCorridor)[r.nextInt(Settings.square.get(numberCorridor).length)];

                break;
        }
        this.numberCorridor = numberCorridor;
    }

    public Room(Integer t[][]) {
        this.content = t;
        this.type = -1;
        for (Integer[] is : content) {
            for (int i : is) {
                if(i == 30 || i == 60 || i == 90 || i == 120) {
                    this.numberCorridor++;
                }
            }
        }
    }

    public String toString() {
        return Integer.toString(this.type);
    }

    /*
     * corridor east => 90
     * corridor north => 120
     * corridor west => 30
     * corridor south => 60
     * corridor placement => 140
     *
     * chest => 45
     * door_left => 80
     * door_right => 81
     * door_app => 82
     * wall => 128
     * air => 0
     * extern => 255
     * lava => 200
     */
    public String show() {
        String txt = "";
        for (Integer[] is : content) {
            for (int i : is) {
                switch (i) {
                    case 200:
                        txt+="~ ";
                        break;

                    case 255:
                        txt+="x ";
                        break;

                    case 0:
                        txt+="  ";
                        break;

                    case 128:
                        txt+="# ";
                        break;

                    case 80:
                        txt+="[]";
                        break;

                    case 81:
                        txt+="[]";
                        break;

                    case 83:
                        txt+="[]";
                        break;

                    case 45:
                        txt+="@ ";
                        break;

                    case 90:
                        txt+="<-";
                        break;

                    case 120:
                        txt+="/\\";
                        break;

                    case 30:
                        txt+="->";
                        break;

                    case 60:
                        txt+="\\/";
                        break;

                    default:
                        txt+="&!";
                        break;
                }
            }
            txt+="\n";
        }
        txt+="\n";
        return txt;
    }
}