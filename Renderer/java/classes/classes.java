package classes;

import java.util.*;

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
	
	public static class PolarVec{
		public double magnitude;
		public double theta;
		public double phi;
		
		public PolarVec(double magnitude, double theta, double phi) {
			this.magnitude = magnitude;
			this.theta = theta;
			this.phi = phi;
		}
		
		public static ScreenCoord toScreen(PolarVec point) {
			ScreenCoord result = new ScreenCoord(0,0);
			result.x = (int)Math.round((point.theta + (0.5*Camera.fovX))*Camera.screenWidth/Camera.fovX);
			result.y = (int)Math.round((1-((point.phi+(0.5*Camera.fovY))/Camera.fovY))*Camera.screenHeight);
			
			return result;
		}
		
		public static ScreenCoord[] toScreen(PolarVec[] points) {
			ScreenCoord[] result = new ScreenCoord[points.length];
			for (int i = 0; i < points.length; i++) {
				result[i] = PolarVec.toScreen(points[i]);
			}
			return result;
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
		
		public static PolarVec toPolar(Vec point) {
			PolarVec result = new PolarVec(0, 0, 0);
			result.magnitude= Math.sqrt(Math.pow(point.x, 2)+Math.pow(point.y, 2)+Math.pow(point.z, 2));
			result.theta = Math.atan(point.x/point.z);
			result.phi = Math.atan(point.y/result.magnitude);
			if (Double.isNaN(result.theta)) result.theta = 0.01;
			//if (result.theta > Math.PI) result.theta = -(2*Math.PI - result.theta);
			//if (result.phi > Math.PI) result.theta = -(2*Math.PI - result.phi);
			return result;
		}
		
		public static PolarVec[] toPolar(Vec[] points) {
			PolarVec[] result = new PolarVec[points.length];
			for (int i = 0; i < points.length; i++) {
				result[i] = Vec.toPolar(points[i]);
			}
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
				ScreenCoord point1 = PolarVec.toScreen(Vec.toPolar(tempObj.tris[i].absTri[0]));
				ScreenCoord point2 = PolarVec.toScreen(Vec.toPolar(tempObj.tris[i].absTri[1]));
				ScreenCoord point3 = PolarVec.toScreen(Vec.toPolar(tempObj.tris[i].absTri[2]));
				g.drawLine(point1.x, point1.y, point2.x, point2.y);
				g.drawLine(point2.x, point2.y, point3.x, point3.y);
				g.drawLine(point1.x, point1.y, point3.x, point3.y);
				if (tempObj.id == 3) System.out.println("1:" + point1.x + " "+point1.y + "  2:" + point2.x + " "+point2.y + "  3:" + point3.x + " "+point3.y);
			}
			System.out.println("Tris complete "+ tempObj.id);
			
			g.dispose();
			return result;
		}
		
		public static GameObject stlToGameObject(String filePath, Vec origin) throws FileNotFoundException, IOException {
			File file = new File(filePath);
			Reader input = new FileReader(file);
			
			
			
			input.close();
			return null;
		}
	}
}