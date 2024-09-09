package resources;

import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class roomutils {
	
	public static String stringify(int[] in) {
		String result = "";
		for (int i : in) result += String.format("%d", i);
		return result;
	}
	
	//////////////////////
	//// VECTOR CLASS ////
	//////////////////////
	public static class Vec{
		public float x;
		public float y;
		public float[] value;
		
		public Vec() {}
		public Vec(float x, float y) {
			this.x = x;
			this.y = y;
			this.value = new float[] {x,y};
		}
		
		public Vec Add(Vec vec1, Vec vec2) {
			Vec result = new Vec();
			result.x = vec1.x + vec2.x;
			result.y = vec1.y + vec2.y;
			return result;
		}
		public Vec Add(Vec vec) {
			return new Vec(this.x + vec.x, this.y + vec.y);
		}
		
		public static int InvertDir(int dir) {
			if (dir+2<4) return dir+2;
			else return dir-2;
		}
	}
		
	////////////////////
	//// ROOM CLASS ////
	////////////////////
	public static class Room{
		public Vec pos = new Vec();
		public int[] slots;
		public int source;
		public int type = 15;
		
		//Room for x and y
		public Room(int x, int y) {
			this.pos.x = x;
			this.pos.y = y;
			this.slots = null;
		}
		
		//room for x, y, slots and source
		public Room(int x, int y, int[] slots, int source) {
			this.pos.x = x;
			this.pos.y = y;
			this.slots = slots;
			this.source = source;
		}
		
		//room for Vec
		public Room(Vec pos) {
			this.pos = pos;
			this.slots = null;
		}

		//room for Vec, and source
		public Room(Vec pos, int source) {
			this.pos = pos;
			this.slots = new int[4];
			this.source = source;
		}
		
		//room for Vec, slots and source
		public Room(Vec pos, int[] slots, int source) {
			this.pos = pos;
			this.slots = slots;
			this.source = source;
		}
		
		public static int identify(Room r) {
			int[] temp = new int[4];
			
			temp = Arrays.copyOf(r.slots, r.slots.length);
			temp[r.source] = 1;
			
			switch (stringify(temp)){
				case "0101":
					return 1;
				case "1010":
					return 2;
					
				case "1000":
					return 3;
				case "0100":
					return 4;
				case "0010":
					return 5;
				case "0001":
					return 6;
					
				case "1100":
					return 7;
				case "0110":
					return 8;
				case "0011":
					return 9;
				case "1001":
					return 10;
				
				case "1101":
					return 11;
				case "1110":
					return 12;
				case "0111":
					return 13;
				case "1011":
					return 14;
				
				case "1111":
					return 0;
				
				default:
					return 15;
			}
		}
	}
	
	/////////////////////
	//// WORLD CLASS ////
	/////////////////////
	public static class World{
		Random ran = new Random();
		public Room[][] map;
		public Vector<Room> toProcess;
		public Vec source;		
		
		public String[][] roomShapes = new String[][] {
			{"-0-","000","-0-"},
			
			{"---","000","---"},
			{"-0-","-0-","-0-"},
			
			{"-0-","-0-","---"},
			{"---","-00","---"},
			{"---","-0-","-0-"},
			{"---","00-","---"},
			
			{"-0-","-00","---"},
			{"---","-00","-0-"},
			{"---","00-","-0-"},
			{"-0-","00-","---"},
			
			{"-0-","000","---"},
			{"-0-","-00","-0-"},
			{"---","000","-0-"},
			{"-0-","00-","-0-"},
			
			{"---","---","---"}
		};
		
		Map<Integer, Vec> dir2vec = new HashMap<Integer,Vec>();
		
		public int width;
		public int height;
		
		public void printWorld() {
			for (Room[] i : this.map) {
				for (int k=0; k < 3; k++) {
					for (Room j : i) {
						if (-1 < j.type && j.type < 16 ) {
							System.out.print(roomShapes[j.type][k]);
						} else {
							System.out.print(roomShapes[15][k]);
						}
					}
					System.out.println();
				}
			}
		}
		
		public Room getRoom(Vec pos) {
			return map[Math.round(pos.y)][Math.round(pos.x)];
		}
		
		public Room[] extendRoom(Room r) {
			ArrayList<Room> rooms = new ArrayList<Room>();
			for (int i = 0; i < r.slots.length; i++) {
				if (r.slots[i] == 1 && getRoom(dir2vec.get(i).Add(r.pos)).type == 15) {
					System.out.println(i);
					Room temp = new Room(dir2vec.get(i).Add(r.pos));
					temp.slots = new int[] {0,0,0,0};
					temp.source = Vec.InvertDir(i);
					//System.out.println("room made");
					int totalSlots = ran.nextInt(0, 3);
					while (totalSlots > 0) {
						int ranSlot = ran.nextInt(0,4);
						//System.out.print(String.format("%d, %d, %d", totalSlots, ranSlot, temp.source));
						if (ranSlot != temp.source) {
							temp.slots[ranSlot] = 1;
							totalSlots -= 1;
							//System.out.println("  node generated");
						} else; //System.out.println(" X");
					}
					
					if (temp.pos.x <= 0) temp.slots[3] = 0;
					else if (temp.pos.x >= map[0].length-1) temp.slots[1] = 0;
					if (temp.pos.y <= 0) temp.slots[0] = 0;	
					else if (temp.pos.y >= map.length-1) temp.slots[2] = 0;
					
					temp.type = Room.identify(temp);
					//System.out.println("Made room of type" + temp.type + "at position X:" + temp.pos.x + " Y:" + temp.pos.y);
					rooms.add(temp);
					toProcess.add(temp);
				}
			}
		
			
			for (Room i : rooms) {
				map[(int) i.pos.y][(int) i.pos.x] = i;
			}
			
			return Arrays.copyOf(rooms.toArray(), rooms.toArray().length, Room[].class);
		}
		
		public World(int[] size, Vec s) {
			dir2vec.put(0, new Vec(0,-1));
			dir2vec.put(1, new Vec(1,0));
			dir2vec.put(2, new Vec(0,1));
			dir2vec.put(3, new Vec(-1,0));
			
			toProcess = new Vector<Room>();
			
			source = s;
			System.out.println(s.value);
			map = new Room[size[1]][size[0]];
			for (int w = 0; w < size[1]; w++) {
				for (int z = 0; z < size[0]; z++) {
					if (z == s.x && w == s.y) {
						System.out.println(String.format("Source Allocated at %d, %d", z, w));
						map[w][z] = new Room(z, w, new int[] {1,1,1,1}, -1);
						map[w][z].type = 0;
						toProcess.add(map[w][z]);
					}
					else {
						map[w][z] = new Room(z,w);
					}
				}
			}
		}
	}
}
