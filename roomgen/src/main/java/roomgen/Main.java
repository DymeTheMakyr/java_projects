package roomgen;

import java.util.*;

import resources.roomutils.*;

public class Main {
	
	public Random ran = new Random(); 
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);	
		World world = new World();
		world.init(new int[] {5, 5}, new Vec(2,2));
		world.toProcess.add(world.getRoom(new Vec(2,2)));
		
		while (true) {
			world.printWorld();
			if (input.nextLine().toLowerCase() == "e") break;
			else {for (Room i : world.toProcess) world.extendRoom(i);}
		}
		
		input.close();
	}
}
