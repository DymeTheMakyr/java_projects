package classes;

import java.util.*;
import java.lang.Math;

public class classes {
	public static class ScreenCoord{
		double x;
		double y;
		
		public ScreenCoord(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}
	
	public static class PolarVec{
		double magnitude;
		double theta;
		double phi;
		
		public PolarVec(double magnitude, double theta, double phi) {
			this.magnitude = magnitude;
			this.theta = theta;
			this.phi = phi;
		}
		
		public static ScreenCoord toScreen(PolarVec point) {
			ScreenCoord result = new ScreenCoord(0,0);
			result.x = point.magnitude * Math.tan(point.theta);
			result.y = point.magnitude * Math.tan(point.phi);
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
		double x = 0.0;
		double y = 0.0;
		double z = 0.0;
		
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
			
			result.magnitude = Vec.distance(Vec.zero, point);
			result.theta = Math.atan(point.x/point.z);
			result.phi = Math.atan(point.y/point.z);
			
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
		Vec[] points;
		PolarVec[] polarPoints;
		ScreenCoord[] screenPoints;
		
		Vec origin;
		
		public GameObject(Vec o, Vec[] p) {
			this.origin = o;
			this.points = p;
			this.polarPoints = Vec.toPolar(p);
			this.screenPoints = PolarVec.toScreen(this.polarPoints);
		}
	}
}
