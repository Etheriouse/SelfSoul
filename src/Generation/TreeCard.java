package Generation;

import java.util.*;

public class TreeCard {

  	private TreeCard right;
  	private TreeCard left;
  	private TreeCard straight;
	private int room[][];
	private int numberChest;
	private int numberEntree[];
	private boolean exit = false;

	/**
	 * Les entree sont droite tout droit gauche, 0 1 2
	 * @param entree le tableau contenant d'autre "boite de jeu"
	 */
  	private TreeCard(int room[][], TreeCard... entree) {
    	if (entree.length > 3) {
    	  	throw new IllegalArgumentException("to many entree");
    	}
    	this.right = entree[0];
    	this.straight = entree[1];
    	this.left = entree[2];
		this.room = new int[room.length][room[0].length];
		for(int i = 0; i<room.length; i++) {
			for(int j = 0; j<room[0].length; j++) {
				this.room[i][j] = room[i][j];
			}
		}
  	}

	public TreeCard(int difficulty) {
		System.out.print("-a");
		TreeCard temp = generateBlock((int) Math.exp((double)difficulty/10.0));
		System.out.print("-b");
		this.left = temp.getLeft();
		this.right = temp.getRight();
		this.straight = temp.getStraight();
		this.numberChest = temp.getChest();
		this.numberEntree = temp.getEntree();
		this.room = new int[temp.getRoom().length][temp.getRoom()[0].length];
		for(int i = 0; i<this.room.length; i++) {
			for(int j = 0; j<this.room[0].length; j++) {
				this.room[i][j] = temp.getRoom()[i][j];
			}
		}
		System.out.print("-tt-");
		Random r = new Random();
		int exit = r.nextInt(this.countAllNoeud());
		//System.out.println("exit is: " + exit + " for total: " + this.countAllNoeud());
		attribuateExit(exit, 0);
		System.out.print("ttt");
	}

	private int attribuateExit(int placement, int follow) {
		//System.out.println(follow);
		if(placement==follow) {

			this.exit = true;
		}
		if(this.left != null) {
			follow = this.left.attribuateExit(placement, follow+1);
		} else {
			follow++;
		}
		if(placement==follow) {
			 this.exit = true;
		}
		if(this.straight != null) {
			follow = this.straight.attribuateExit(placement, follow+1);
		}else {
			follow++;
		}
		if(placement==follow) {
			 this.exit = true;
		}
		if(this.right != null) {
			follow = this.right.attribuateExit(placement, follow+1);
		}else {
			follow++;
		}
		if(placement==follow) {
			this.exit = true;
	   }
		return follow;
	}

	private TreeCard generateBlock(int numberOfSale) {
		TreeCard floor;
		int entres[];
		int room[][];
		Random r = new Random();

		if(numberOfSale>1) {
			int numberEntree = r.nextInt(4);
			entres = new int[numberEntree];
			room = generateRoom(0, entres);
			if(numberEntree==0) {
				floor = new TreeCard(room, null, null, null).setChest(contain(room, 2)).setEntree(entres);;
				return floor;
			} else {
				boolean droite = false;
				boolean gauche = false;
				boolean tout = false;
				for(int i = 0; i<numberEntree; i++) {
					switch (entres[i]) {
						case 0:
							gauche = true;
							break;
						case 1:
							tout = true;
							break;

						case 2:
							droite = true;
							break;

						default:
							break;
					}
				}

				floor = new TreeCard(room,
					droite?generateBlock(numberOfSale-1):null,
					tout?generateBlock(numberOfSale-1):null,
					gauche?generateBlock(numberOfSale-1):null
				).setChest(contain(room, 2)).setEntree(entres);
				return floor;
			}
		} else {
			entres = new int[0];
			room = generateRoom(0, entres);
			floor = new TreeCard(room, null, null, null).setChest(contain(room, 2)).setEntree(entres);
			return floor;
		}
	}

	private int contain(int[][] room2, int i) {
		int sum = 0;
		for (int[] js : room2) {
			for (int j : js) {
				if(j == i) {
					sum++;
				}
			}
		}
		return sum;
	}

	public int[][] generateRoom(int difficulty, int entres[]) {
		Random r = new Random();
		int CreateRoom[][];
		/* 0 = air 1 = wall 2 = chest 3 = entree 4 = player  */

		CreateRoom = new int[r.nextInt(14)+7][r.nextInt(14)+7];
		fillArray(CreateRoom, 1);
		generateEntree(CreateRoom, entres);
		//System.out.println("in genetreEntree: " + Arrays.toString(entres));
		int x = r.nextInt(CreateRoom[0].length-2)+1;
		int y = CreateRoom.length-1;
		int move = 2;
		CreateRoom[y][x] = 3;
		boolean alwayGenerate = true;
		int nbmove = 0;
		while(alwayGenerate && nbmove < 50*CreateRoom.length) {
			//System.out.println("x: " + x + " y: " + y);
			switch (move) {
				case 0:
					if(x<CreateRoom[0].length-1) if(CreateRoom[y][x+1] != 3) x+=1;
					break;
				case 1:
					if(x>0) if(CreateRoom[y][x-1] != 3) x-=1;
					break;
				case 2:
					if(y>0) if(CreateRoom[y-1][x] != 3) y-=1;
					break;
				case 3:
					if(y<CreateRoom.length-1) if(CreateRoom[y+1][x] != 3) y+=1;
					break;
				default:
					break;
			}
			move = r.nextInt(4);
			CreateRoom[y][x] = 0;

			if(entres.length > 0) {
				alwayGenerate = !verifyIfEntreeAreAccecibily(CreateRoom);
			}
			nbmove++;
		}

		int numberChest = r.nextInt(5);

        int randomX = 0, randomY = 0;
		System.out.print("-c");
		if(contain(CreateRoom, 0) < 1) {
			System.out.println("kaka");
		}
		for(int n = 0; n<numberChest; n++) {
            boolean good = false;
            while (!good) {
                randomX = r.nextInt(CreateRoom[0].length-1)+1;
                randomY = r.nextInt(CreateRoom.length-1)+1;
                good = CreateRoom[randomY][randomX] != 2 && CreateRoom[randomY][randomX] != 1 && CreateRoom[randomY][randomX] != 3;
			}
            CreateRoom[randomY][randomX] = 2;
        }
		System.out.print("-d");

		return CreateRoom;

	}

	private void generateEntree(int CreateRoom[][], int entres[]) {
		Random r = new Random();
		int entreePlace[] = generateArrayWithtoutCopy(entres.length, 3);
		copyContent(entres, entreePlace);;
		int Xe = 0;
		for(int n = 0; n<entreePlace.length; n++) {
			if(entreePlace[n]%2==0) {
				Xe = r.nextInt(CreateRoom.length-2)+1;
			} else {
				Xe = r.nextInt(CreateRoom[0].length-2)+1;
			}
			switch (entreePlace[n]) {
				case 0:
					CreateRoom[Xe][0] = 3; // left
					break;

				case 1:
					CreateRoom[0][Xe] = 3; // straight
					break;

				case 2:
					CreateRoom[Xe][CreateRoom[0].length-1] = 3; // right
					break;

				default:
					break;
			}
		}

	}

	private boolean verifyIfEntreeAreAccecibily(int CreateRoom[][]) {
		boolean check = true;
		for(int i = 0; i<CreateRoom.length; i++) {
			for(int j = 0; j<CreateRoom[0].length; j++) {
				if(CreateRoom[i][j] == 3) {
					boolean temp = false;
					if(i>0) {
						temp = ( temp || CreateRoom[i-1][j] == 0) ;
					}
					if(j>0) {
						temp = ( temp || CreateRoom[i][j-1] == 0) ;
					}
					if(i<CreateRoom.length-1) {
						temp = ( temp || CreateRoom[i+1][j] == 0) ;
					}

					if(j<CreateRoom[0].length-1) {
						temp = ( temp || CreateRoom[i][j+1] == 0) ;
					}
					check = check && temp;
				}
			}
		}
		return check;
	}

	private static int[] generateArrayWithtoutCopy(int lenght, int max) {
        ArrayList<Integer> t = new ArrayList<Integer>();

		Random r = new Random();

		for(int i = 0; i<lenght; i++) {
			boolean goodnumber = false;
			int temp = r.nextInt(max);
			while(!goodnumber) {
				temp = r.nextInt(max);
				goodnumber = !t.contains(temp);
			}
			t.add(temp);
		}

        return toArray(t);
    }

	private static int[] toArray(ArrayList<Integer> array) {
        int t[] = new int[array.size()];
		for(int n = 0; n<t.length; n++) {
			t[n] = array.get(n);
		}
		return t;
    }

	private static void fillArray(int t[][], int n) {
		for(int i = 0; i<t.length; i++) {
			for(int j = 0; j<t[0].length; j++) {
				t[i][j] = n;
			}
		}
	}

	private static void copyContent(int Array[], int Array2[]) {
		for(int i = 0; i<Array.length; i++) {
			Array[i] = Array2[i];
		}
	}

	public TreeCard getRoom(int ... entree) {
		if(entree.length>1) {
			switch (entree[0]) {
				case 0:
					right.getRoom(deleteFirst(entree));
					break;

				case 1:
					straight.getRoom(deleteFirst(entree));
					break;

				case 2:
					left.getRoom(deleteFirst(entree));
					break;

				default:
					straight.getRoom(deleteFirst(entree));
					break;
			}
		}
		return this;
	}

	public static void main(String[] args) {
		for(int i = 0; i<10000; i++) {
			System.out.print("t");
			TreeCard t = new TreeCard(12);
			System.out.print("-tt");
			if(!t.testExit()) {
				System.out.println(t);
			}
			System.out.println(i);
		}
	}

	public boolean testExit() {
		boolean test = false;
		if(this.left != null) {
			test = test || this.left.testExit();
		}
		if(this.straight != null) {
			test = test || this.straight.testExit();
		}
		if(this.right != null) {
			test = test || this.right.testExit();
		}
		return test || this.exit;
	}

	public int countAllNoeud() {
		int num = 0;
		if(this.left != null) {
			num+=this.left.countAllNoeud();
		} else {
			num+=1;
		}
		if(this.straight != null) {
			num+=this.straight.countAllNoeud();
		} else {
			num+=1;
		}
		if(this.right != null) {
			num+=this.right.countAllNoeud();
		} else  {
			num+=1;
		}
		return num;
	}

	/**
	 *
	 * doit generer une string comme ceci:
	 *
	 *
	 * 		    *
	 * 	  ┌─────┼─────┐
	 	  *		*     *
	 *  ┌─┘   ┌─┴─┐   └─┐
	 * 	*     *   *     *
	 *		┌─┤			├─┐
	 *		* *			* *
	 * lol sa marche pas j'ai abandonner
	 */
	public String toString() {
		return this.printTree();
	}

	private String printTree() {
		String Tree = "";
		Tree = "Room: c: " + this.numberChest + " e: " + Arrays.toString(this.numberEntree) + " exit: " + this.exit+"\n";
		if(this.left != null) {
			Tree+=this.left.printTree();
		}
		if(this.straight != null) {
			Tree+=this.straight.printTree();
		}
		if(this.right != null) {
			Tree+=this.right.printTree();
		}
		return Tree;
	}

	public String description(String txt, int height) {
		String indentation = "";
		for(int i = 0; i<height; i++) {
			indentation+="  ";
		}
		txt+=indentation+"Room " + this.room.length+"/"+this.room[0].length+":\n";
		if(this.left == null) {
			txt+=indentation+"left:     "+"x\n";
		} else {
			txt+=indentation+"left:     "+this.left.description(txt, height+1)+"\n";
		}
		if(this.straight == null) {
			txt+=indentation+"straight:     "+"x\n";
		} else {
			txt+=indentation+"straight:     "+this.straight.description(txt, height+1)+"\n";
		}
		if(this.right == null) {
			txt+=indentation+"right:     "+"x\n";
		} else {
			txt+=indentation+"right:     "+this.right.description(txt, height+1)+"\n";
		}
		//txt+=indentation+"left:     " + this.left==null?"":this.left.toStrings(txt, height);
		//txt+=indentation+"straight: " + this.straight==null?"":this.straight.toStrings(txt, height);
		//txt+=indentation+"right:    " + this.right==null?"":this.right.toStrings(txt, height);
		return txt;
	}

	private int[] deleteFirst(int ... t) {
		int newT[] = new int[t.length-1];
		for(int i = 1; i<t.length; i++) {
			newT[i-1] = t[i];
		}
		return t;
	}

	private static void show(int t[][]) {
		for (int[] is : t) {

			System.out.print("|");
			for (int i : is) {
				if(i == 0) {
					System.out.print("  ");
				} else if(i == 1) {
					System.out.print("# ");
				} else if(i == 3) {
					System.out.print("@ ");
				}
			}
			System.out.println("|");
		}
		System.out.println();
	}

	public TreeCard getLeft() {
		return this.left;
	}


	public TreeCard getRight() {
		return this.right;
	}

	public TreeCard getStraight() {
		return this.straight;
	}

	public int[][] getRoom() {
		return this.room;
	}

	public int getChest() {
		return this.numberChest;
	}

	public int[] getEntree() {
		return this.numberEntree;
	}

	public TreeCard setChest(int chest) {
		this.numberChest = chest;
		return this;
	}

	public TreeCard setEntree(int entree[]) {
		this.numberEntree = entree;
		return this;
	}
}

