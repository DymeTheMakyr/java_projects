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
			Vector<Room> temp = new Vector<Room>(world.toProcess);
			world.toProcess.clear();
			if (input.nextLine().toLowerCase().contains("e")) break;
			else {
				for (Room i : temp) {
					System.out.println("started extension");
					System.out.println(i);
					world.extendRoom(i);
					System.out.println("extended room");
				}
				System.out.println("finished extension");
			}
			System.out.println("single loop complete");
		}
		
		input.close();
	}
}
