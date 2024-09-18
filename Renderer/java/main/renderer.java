package main;

import classes.classes.*;
import java.awt.*;
import java.awt.event.*;

public class renderer extends Frame	{	

	public Vec[] fp = new Vec[] {
		new Vec(10,0,10),
		new Vec(-10,0,10),
		new Vec(-10,0,-10),
		new Vec(10,0,-10),
	};
	public Vec fOrigin = new Vec(0,-1,0);
	public GameObject floor = new GameObject(fOrigin, fp, 
		new Tri[] {
			new Tri(fp[0], fp[1], fp[3], fOrigin),
			new Tri(fp[1], fp[2], fp[3], fOrigin)
	});
	
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
	public Vec oOrigin1 = new Vec(2,0, 7);
	public GameObject obj1 = new GameObject(oOrigin1, op,
		new Tri[] {
			new Tri(op[0], op[1], op[3], oOrigin1),
			new Tri(op[1], op[2], op[3], oOrigin1),
			new Tri(op[1], op[5], op[2], oOrigin1),
			new Tri(op[5], op[6], op[2], oOrigin1),
			new Tri(op[5], op[4], op[6], oOrigin1),
			new Tri(op[4], op[7], op[6], oOrigin1),
			new Tri(op[4], op[0], op[7], oOrigin1),
			new Tri(op[0], op[3], op[7], oOrigin1),
			
			new Tri(op[4], op[5], op[0], oOrigin1),
			new Tri(op[5], op[1], op[0], oOrigin1),
			
			new Tri(op[2], op[3], op[6], oOrigin1),
			new Tri(op[3], op[7], op[6], oOrigin1)
		}
	);
	
	public Vec oOrigin2 = new Vec(-3,0, 9);
	public GameObject obj2 = new GameObject(oOrigin2, op,
		new Tri[] {
			new Tri(op[0], op[1], op[3], oOrigin2),
			new Tri(op[1], op[2], op[3], oOrigin2),
			new Tri(op[1], op[5], op[2], oOrigin2),
			new Tri(op[5], op[6], op[2], oOrigin2),
			new Tri(op[5], op[4], op[6], oOrigin2),
			new Tri(op[4], op[7], op[6], oOrigin2),
			new Tri(op[4], op[0], op[7], oOrigin2),
			new Tri(op[0], op[3], op[7], oOrigin2),
			
			new Tri(op[4], op[5], op[0], oOrigin2),
			new Tri(op[5], op[1], op[0], oOrigin2),
			
			new Tri(op[2], op[3], op[6], oOrigin2),
			new Tri(op[3], op[7], op[6], oOrigin2)
		}
	);
	
	public GameObject[] objects = new GameObject[] {floor, obj1, obj2};
	
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
		int lineRad = 4;
		
				
		for (GameObject tempObj : objects) {
			System.out.println(tempObj.points.length);
			for (int i = tempObj.points.length-1; i >= 0; i-=1) {
				//System.out.println("Processing point" + (i));
				if (tempObj.points[i].z <= 0) {
					System.out.println(String.format("Point Index %d", i+1));
					System.out.println(String.format("CART X: %f | Y: %f | Z: %f", tempObj.absPoints[i].x,tempObj.absPoints[i].y,tempObj.absPoints[i].z));
					System.out.println(String.format("POLR MAG: %f | THETA: %f | PHI: %f", tempObj.polarPoints[i].magnitude, tempObj.polarPoints[i].theta, tempObj.polarPoints[i].phi));
					System.out.println(String.format("X: %f, Y: %f", tempObj.screenPoints[i].x, tempObj.screenPoints[i].y));
					g.setColor(Color.green);
					g.drawOval((int)Math.round(tempObj.screenPoints[i].x)-lineRad,(int)Math.round(tempObj.screenPoints[i].y)-lineRad, 2*lineRad, 2*lineRad);
				}
				else if (tempObj.points[i].z >= 0) {
					System.out.println(String.format("Point Index %d", i+1));
					System.out.println(String.format("CART X: %f, Y: %f, Z: %f", tempObj.absPoints[i].x,tempObj.absPoints[i].y,tempObj.absPoints[i].z));
					System.out.println(String.format("MAG: %f | THETA: %f | PHI: %f", tempObj.polarPoints[i].magnitude, tempObj.polarPoints[i].theta, tempObj.polarPoints[i].phi));
					System.out.println(String.format("X: %f, Y: %f", tempObj.screenPoints[i].x, tempObj.screenPoints[i].y));
					g.setColor(Color.red);
					g.drawOval((int)Math.round(tempObj.screenPoints[i].x)-lineRad,(int)Math.round(tempObj.screenPoints[i].y)-lineRad, 2*lineRad, 2*lineRad);
				}
				else {
					System.out.println("didnt draw" + i + "due to z index");
				}
			}
			
			g.setColor(Color.black);
			
			for (int i = tempObj.tris.length-1; i >= 0; i--) {
				ScreenCoord point1 = PolarVec.toScreen(Vec.toPolar(tempObj.tris[i].absTri[0]));
				ScreenCoord point2 = PolarVec.toScreen(Vec.toPolar(tempObj.tris[i].absTri[1]));
				ScreenCoord point3 = PolarVec.toScreen(Vec.toPolar(tempObj.tris[i].absTri[2]));
				g.drawLine((int)Math.round(point1.x), (int)Math.round(point1.y), (int)Math.round(point2.x), (int)Math.round(point2.y));
				g.drawLine((int)Math.round(point2.x), (int)Math.round(point2.y), (int)Math.round(point3.x), (int)Math.round(point3.y));
				g.drawLine((int)Math.round(point1.x), (int)Math.round(point1.y), (int)Math.round(point3.x), (int)Math.round(point3.y));
			}
		}
	}
	
	public static void main(String[] args) { 
        new renderer(); 
    } 
}
