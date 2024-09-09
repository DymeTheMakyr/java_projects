package resources;

import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class roomutils {
		
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
	}
	
	/////////////////////
	//// WORLD CLASS ////
	/////////////////////
	public static class World{
		Random ran = new Random();
		public Room[][] map;
		public Vector<Room> toProcess;
		public Vec source;
		
		Map<Integer, Vec> dir2vec = new HashMap<Integer,Vec>();
		Map<Integer[], Integer> vec2dir = new HashMap<Integer[],Integer>();
		
		public int width;
		public int height;
		
		public void printWorld() {
			for (Room[] i : this.map) {
				String row = "";
				for (Room j : i) {
					if (j.slots != null) {
						row += "0";
					} else {
						row += "-";
					}
				}
				
				System.out.println(row);
			}
		}
		
		public Room getRoom(Vec pos) {
			return map[Math.round(pos.y)][Math.round(pos.x)];
		}
		
		public Room[] extendRoom(Room r) {
			ArrayList<Room> rooms = new ArrayList<Room>();
			for (int i = 0; i < r.slots.length; i++) {
				if (r.slots[i] == 1) {
					System.out.println(i);
					Room temp = new Room(dir2vec.get(i).Add(r.pos));
					temp.slots = new int[] {0,0,0,0};
					temp.source = Vec.InvertDir(i);
					System.out.println("room made");
					int totalSlots = ran.nextInt(0, 3);
					while (totalSlots > 0) {
						int ranSlot = ran.nextInt(0,4);
						System.out.print(String.format("%d, %d, %d", totalSlots, ranSlot, temp.source));
						if (ranSlot != temp.source) {
							temp.slots[ranSlot] = 1;
							totalSlots -= 1;
							System.out.println("  node generated");
						} else System.out.println(" X");
					}
					rooms.add(temp);
					toProcess.add(temp);
				}
			}
			
			for (Room i : rooms) {
				map[(int) i.pos.y][(int) i.pos.x] = i;
			}
			
			return Arrays.copyOf(rooms.toArray(), rooms.toArray().length, Room[].class);
		}
		
		public void init(int[] size, Vec s) {
			dir2vec.put(0, new Vec(0,-1));
			dir2vec.put(1, new Vec(1,0));
			dir2vec.put(2, new Vec(0,1));
			dir2vec.put(3, new Vec(-1,0));
			
			vec2dir.put(new Integer[]{0,-1}, 0);
			vec2dir.put(new Integer[] {1,0}, 1);
			vec2dir.put(new Integer[] {0,1}, 2);
			vec2dir.put(new Integer[]{-1,0}, 3);
			
			toProcess = new Vector<Room>();
			
			source = s;
			System.out.println(s.value);
			map = new Room[size[1]][size[0]];
			for (int w = 0; w < size[1]; w++) {
				for (int z = 0; z < size[0]; z++) {
					if (z == s.x && w == s.y) {
						System.out.println(String.format("Source Allocated at %d, %d", z, w));
						map[w][z] = new Room(z, w, new int[] {1,1,1,1}, -1);
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
