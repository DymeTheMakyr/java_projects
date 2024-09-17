package main;

import classes.classes.*;
import java.awt.*;
import java.awt.event.*;

public class renderer extends Frame	{	
	public Vec[] op =  new Vec[] {
			new Vec(1,1,1),
			new Vec(-1,1,1),
			new Vec(-1,-1,1),
			new Vec(1,-1,1),
			new Vec(1,1,-1),
			new Vec(-1,1,-1),
			new Vec(-1,-1,-1),
			new Vec(1,-1,-1)
		};
	public GameObject obj1 = new GameObject(new Vec(0,0, 5), op,
		new Tri[] {
			new Tri(op[0], op[1], op[3]),
			new Tri(op[1], op[2], op[3]),
			new Tri(op[1], op[5], op[2]),
			new Tri(op[5], op[6], op[2]),
			new Tri(op[5], op[4], op[6]),
			new Tri(op[4], op[7], op[6]),
			new Tri(op[4], op[0], op[7]),
			new Tri(op[0], op[3], op[7]),
			
			new Tri(op[4], op[5], op[0]),
			new Tri(op[5], op[1], op[0]),
			
			new Tri(op[2], op[3], op[6]),
			new Tri(op[3], op[7], op[6])
		}
	);
	
	public renderer() {
		setVisible(true);
		setSize(Camera.screenWidth,Camera.screenHeight);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	public void paint(Graphics g) {
		g.drawLine(0, (int)Math.round(0.5*Camera.screenHeight), (int)Math.round(Camera.screenWidth), (int)Math.round(0.5*Camera.screenHeight));
		System.out.println(obj1.points.length);
		for (int i = obj1.points.length-1; i >= 0; i-=1) {
			//System.out.println("Processing point" + (i));
			if (obj1.points[i].z <= 0) {
				System.out.println(String.format("Point Index %d", i+1));
				System.out.println(String.format("CART X: %f | Y: %f | Z: %f", obj1.absPoints[i].x,obj1.absPoints[i].y,obj1.absPoints[i].z));
				System.out.println(String.format("POLR MAG: %f | THETA: %f | PHI: %f", obj1.polarPoints[i].magnitude, obj1.polarPoints[i].theta, obj1.polarPoints[i].phi));
				System.out.println(String.format("X: %f, Y: %f", obj1.screenPoints[i].x, obj1.screenPoints[i].y));
				g.setColor(Color.green);
				g.drawOval((int)Math.round(obj1.screenPoints[i].x),(int)Math.round(obj1.screenPoints[i].y), 4, 4);
			}
			else if (obj1.points[i].z >= 0) {
				System.out.println(String.format("Point Index %d", i+1));
				System.out.println(String.format("CART X: %f, Y: %f, Z: %f", obj1.absPoints[i].x,obj1.absPoints[i].y,obj1.absPoints[i].z));
				System.out.println(String.format("MAG: %f | THETA: %f | PHI: %f", obj1.polarPoints[i].magnitude, obj1.polarPoints[i].theta, obj1.polarPoints[i].phi));
				System.out.println(String.format("X: %f, Y: %f", obj1.screenPoints[i].x, obj1.screenPoints[i].y));
				g.setColor(Color.red);
				g.drawOval((int)Math.round(obj1.screenPoints[i].x),(int)Math.round(obj1.screenPoints[i].y), 4, 4);
			}
			else {
				System.out.println("didnt draw" + i + "due to z index");
			}
		}
	}
	
	public static void main(String[] args) { 
        new renderer(); 
    } 
}
