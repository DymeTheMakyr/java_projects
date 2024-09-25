package classes;

import java.util.*;
import ejml.*;
import java.awt.*;
import java.awt.image.*;
import java.lang.Math;
import java.io.*;

public class classes {
	public static class Camera{
		public static double fovX = 0.5*Math.PI;
		public static double fovY = 0.25*Math.PI;
		public static int screenWidth = 900;
		public static int screenHeight = 450;
		public static int xOffset = Math.round(screenHeight)/2;
		public static int yOffset = Math.round(screenHeight/2);
	}
	
	public static class ScreenCoord{
		public int x;
		public int y;
		
		public ScreenCoord(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	
	public static class Vec {
		public double x = 0.0;
		public double y = 0.0;
		public double z = 0.0;
		
		public static Vec zero = new Vec(0,0,0);
	
		public Vec(double x, double y) {
			this.x = x;
			this.y = y;
			this.z = 0;
		}
		public Vec(double x, double y, double z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		public static Vec Add(Vec vec1, Vec vec2) {
			return new Vec(vec1.x + vec2.x, vec1.y + vec2.y, vec1.z + vec2.z);
		}
		

		public Vec Add(Vec vec1) {
			return new Vec(vec1.x + this.x, vec1.y + this.y, vec1.z + this.z);
		}
		
		public static double distance(Vec vec1, Vec vec2) {
			return Math.sqrt((Math.pow(vec2.x - vec1.x, 2) + Math.pow(vec2.y-vec1.y,2) + Math.pow(vec2.z - vec1.z, 2)));
		}	
		
		public ScreenCoord VecToScreen(Vec vec) {
			ScreenCoord result = new ScreenCoord(0, 0);
			
			
			
			return result;
		}
	}
	
	public static class Tri{
		public Vec[] tri;
		public Vec[] absTri;
		public Vec origin;
		public Tri(Vec vec1, Vec vec2, Vec vec3, Vec origin) {
			this.tri = new Vec[]{vec1, vec2, vec3};
			this.absTri = new Vec[] {vec1.Add(origin),vec2.Add(origin),vec3.Add(origin)};
			this.origin = origin;
		}
	}
	
	public static class GameObject {
		public Tri[] tris;
		public int id;
		
		public Vec origin;
		
		public GameObject(Vec o,  Tri[] t) {
			this.origin = o;
			this.tris = t;
			this.id = 0;
		}
		public GameObject(Vec o, Tri[] t, int id) {
			this.origin = o;
			this.tris = t;
			this.id = id;
		}
	}
	
	public static class Compute{
		public static int lineRad = 4;
		
		public static BufferedImage drawGameObject(GameObject tempObj) {
			BufferedImage result = new BufferedImage(Camera.screenWidth, Camera.screenWidth, BufferedImage.TYPE_INT_ARGB_PRE);
			Graphics2D g = (Graphics2D)result.getGraphics();
			g.setBackground(new Color(0,true));
			g.clearRect(0, 0, Camera.screenWidth, Camera.screenHeight);
			
			g.setColor(Color.cyan);
			ScreenCoord origin = PolarVec.toScreen(Vec.toPolar(tempObj.origin));
			g.drawOval((int)origin.x-lineRad, (int)origin.y-lineRad, 2*lineRad, 2*lineRad);
			
			g.setColor(Color.black);
			
			System.out.println("Tris");
			
			for (int i = tempObj.tris.length-1; i >= 0; i--) {
////				ScreenCoord point1 = PolarVec.toScreen(Vec.VecToScreen(tempObj.tris[i].absTri[0]));
////				ScreenCoord point2 = PolarVec.toScreen(Vec.toPolar(tempObj.tris[i].absTri[1]));
////				ScreenCoord point3 = PolarVec.toScreen(Vec.toPolar(tempObj.tris[i].absTri[2]));
//				g.drawLine(point1.x, point1.y, point2.x, point2.y);
//				g.drawLine(point2.x, point2.y, point3.x, point3.y);
//				g.drawLine(point1.x, point1.y, point3.x, point3.y);
//				if (tempObj.id == 3) System.out.println("1:" + point1.x + " "+point1.y + "  2:" + point2.x + " "+point2.y + "  3:" + point3.x + " "+point3.y);
			}
			System.out.println("Tris complete "+ tempObj.id);
			
			g.dispose();
			return result;
		}
		
		public static GameObject stlToGameObject(String filePath, Vec origin, double scale) {
			try {
				File file = new File(filePath);
				Scanner input = new Scanner(file);
				
				ArrayList<Tri> tempTris = new ArrayList<>();
				
				boolean running = true;
				while (running) {
					String[][] row = new String[3][4];
					String temp = input.nextLine();
					
					if (temp.toLowerCase().contains("vertex")) {
						row[0] = temp.trim().split("[\\s]");
						row[1] = input.nextLine().trim().split("[\\s]");
						row[2] = input.nextLine().trim().split("[\\s]");
						
						System.out.println("vert 1 " + String.join(" | ", row[0]));
						System.out.println("vert 2 "+ String.join(" | ", row[1]));
						System.out.println("vert 3 "+String.join(" | ", row[2]));
						
						Vec vec1 = new Vec(Double.parseDouble(row[0][1])*scale,Double.parseDouble(row[0][2])*scale,Double.parseDouble(row[0][3])*scale);
						Vec vec2 = new Vec(Double.parseDouble(row[1][1])*scale,Double.parseDouble(row[1][2])*scale,Double.parseDouble(row[1][3])*scale);
						Vec vec3 = new Vec(Double.parseDouble(row[2][1])*scale,Double.parseDouble(row[2][2])*scale,Double.parseDouble(row[2][3])*scale);
						
						tempTris.add(new Tri(vec1, vec2, vec3, origin));
					}
					
					if (!input.hasNextLine()) {
						running = false;
						break;
					}
				}
				
				Tri[] finalTris = new Tri[tempTris.size()];
				finalTris = tempTris.toArray(finalTris);
				GameObject result = new GameObject(origin, finalTris);
				
				input.close();
				return result;
			} catch (Exception e){
				System.out.println("File not found" + e);
			}
			
			return null;
		}
	}
}