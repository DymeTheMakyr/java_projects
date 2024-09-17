package classes;

import java.util.*;

import java.lang.Math;

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
		public double x;
		public double y;
		
		public ScreenCoord(double x, double y) {
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
			result.x = (point.theta + (0.5*Camera.fovX))*Camera.screenWidth/Camera.fovX;
			result.y = (1-((point.phi+(0.5*Camera.fovY))/Camera.fovY))*Camera.screenHeight;
			
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
	public static class GameObject {
		public Vec[] points;
		public Vec[] absPoints;
		public PolarVec[] polarPoints;
		public ScreenCoord[] screenPoints;
		
		public Vec origin;
		
		public GameObject(Vec o, Vec[] p) {
			this.origin = o;
			this.points = p;
			this.absPoints = Arrays.copyOf(p, p.length);
			Vec[] temp = new Vec[p.length];
			for (int i = 0; i < p.length; i++) {
				temp[i] = this.absPoints[i].Add(o);
			}
			this.absPoints = temp;
			this.polarPoints = Vec.toPolar(this.absPoints);
			this.screenPoints = PolarVec.toScreen(this.polarPoints);
		}
	}
}