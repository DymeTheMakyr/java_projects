package roomgen;

import java.util.*;

import resources.roomutils.*;

public class Main {
	
	public Random ran = new Random(); 
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);	
		World world = new World(new int[] {11, 7}, new Vec(5,3));
		
		while (true) {
			Vector<Room> temp;
			while (world.toProcess.toArray().length > 0) {
				world.printWorld();
				temp = new Vector<Room>(world.toProcess);
				world.toProcess.clear();
				for (Room i : temp) {
					//System.out.println("started extension");
					//System.out.println(i);
					world.extendRoom(i);
					//System.out.println("extended room");
				}	
			}
			
			if (input.nextLine().contains("e")) break;
			else {
				world = new World(new int[] {15, 7}, new Vec(5,3));
				world.printWorld();
				temp = new Vector<Room>(world.toProcess);
			}
		}
		
		input.close();
	}
}
