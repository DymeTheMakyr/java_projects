package main;

import classes.classes.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.lang.Thread;
import java.util.ArrayList;
import 


public class renderer extends Frame	{	

	public Vec[] fp = new Vec[] {
		new Vec(3000,0,3000),
		new Vec(-3000,0,3000),
		new Vec(-3000,0,0),
		new Vec(3000,0,0),
	};
	public Vec fOrigin = new Vec(0,-1,0);
	public GameObject floor = new GameObject(fOrigin, 
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
	public Vec oOrigin1 = new Vec(2,0, 8);
	public GameObject obj1 = new GameObject(oOrigin1,
		new Tri[] {
			new Tri(op[0], op[3], op[1], oOrigin1),
			new Tri(op[1], op[3], op[2], oOrigin1),
			new Tri(op[1], op[2], op[5], oOrigin1),
			new Tri(op[5], op[2], op[6], oOrigin1),
			new Tri(op[5], op[6], op[4], oOrigin1),
			new Tri(op[4], op[6], op[7], oOrigin1),
			new Tri(op[4], op[7], op[0], oOrigin1),
			new Tri(op[0], op[7], op[3], oOrigin1),
			
			new Tri(op[4], op[0], op[5], oOrigin1),
			new Tri(op[5], op[0], op[1], oOrigin1),
		
			new Tri(op[2], op[6], op[3], oOrigin1),
			new Tri(op[3], op[6], op[7], oOrigin1)
		}
	);
	
	public Vec oOrigin2 = new Vec(-2,0, 12);
	public GameObject obj2 = new GameObject(oOrigin2,
		new Tri[] {
			new Tri(op[0], op[3], op[1], oOrigin2),
			new Tri(op[1], op[3], op[2], oOrigin2),
			new Tri(op[1], op[2], op[5], oOrigin2),
			new Tri(op[5], op[2], op[6], oOrigin2),
			new Tri(op[5], op[6], op[4], oOrigin2),
			new Tri(op[4], op[6], op[7], oOrigin2),
			new Tri(op[4], op[7], op[0], oOrigin2),
			new Tri(op[0], op[7], op[3], oOrigin2),
			
			new Tri(op[4], op[0], op[5], oOrigin2),
			new Tri(op[5], op[0], op[1], oOrigin2),
			
			new Tri(op[2], op[6], op[3], oOrigin2),
			new Tri(op[3], op[6], op[7], oOrigin2)
		}
	);
	
	public Vec oOrigin3 = new Vec(0,-2, 14);
	public GameObject obj3 = new GameObject(oOrigin3,
		new Tri[] {
			new Tri(op[0], op[3], op[1], oOrigin3),
			new Tri(op[1], op[3], op[2], oOrigin3),
			new Tri(op[1], op[2], op[5], oOrigin3),
			new Tri(op[5], op[2], op[6], oOrigin3),
			new Tri(op[5], op[6], op[4], oOrigin3),
			new Tri(op[4], op[6], op[7], oOrigin3),
			new Tri(op[4], op[7], op[0], oOrigin3),
			new Tri(op[0], op[7], op[3], oOrigin3),
			
			new Tri(op[4], op[0], op[5], oOrigin3),
			new Tri(op[5], op[0], op[1], oOrigin3),
			
			new Tri(op[2], op[6], op[3], oOrigin3),
			new Tri(op[3], op[6], op[7], oOrigin3)
		}, 0
	);
	
	public Vec oOrigin4 = new Vec(0,2, 10);
	public GameObject obj4 = new GameObject(oOrigin4,
		new Tri[] {
			new Tri(op[0], op[3], op[1], oOrigin4),
			new Tri(op[1], op[3], op[2], oOrigin4),
			new Tri(op[1], op[2], op[5], oOrigin4),
			new Tri(op[5], op[2], op[6], oOrigin4),
			new Tri(op[5], op[6], op[4], oOrigin4),
			new Tri(op[4], op[6], op[7], oOrigin4),
			new Tri(op[4], op[7], op[0], oOrigin4),
			new Tri(op[0], op[7], op[3], oOrigin4),
		
			new Tri(op[4], op[0], op[5], oOrigin4),
			new Tri(op[5], op[0], op[1], oOrigin4),
			
			new Tri(op[2], op[6], op[3], oOrigin4),
			new Tri(op[3], op[6], op[7], oOrigin4)
		}, 0
	);
	
	GameObject stlObj1 = Compute.stlToGameObject("C:\\Users\\Hugo\\eclipse-workspace\\Renderer\\java\\main\\default.stl", new Vec(-3,2,6), 1);
	GameObject stlObj2 = Compute.stlToGameObject("C:\\Users\\Hugo\\eclipse-workspace\\Renderer\\java\\main\\default.stl", new Vec(3,2,6), 1);
	GameObject stlObj3 = Compute.stlToGameObject("C:\\Users\\Hugo\\eclipse-workspace\\Renderer\\java\\main\\default.stl", new Vec(-3,-2,6), 1);
	GameObject stlObj4 = Compute.stlToGameObject("C:\\Users\\Hugo\\eclipse-workspace\\Renderer\\java\\main\\default.stl", new Vec(3,-2,6), 1);
	
	public GameObject[] objects = new GameObject[] {stlObj1, stlObj2, stlObj3, stlObj4};
	
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
		ArrayList<Thread> threads = new ArrayList<>();
		
		ArrayList<BufferedImage> frames = new ArrayList<>();
		g.setColor(Color.black);
		g.drawLine(0, (int)Math.round(0.5*Camera.screenHeight), Camera.screenWidth, (int)Math.round(0.5*Camera.screenHeight));
		g.setColor(Color.gray);
		g.drawLine((int)Math.round(0.5*Camera.screenWidth), (int)Math.round(0.5*Camera.screenHeight), 0, (int)Camera.screenHeight);
		g.drawLine((int)Math.round(0.5*Camera.screenWidth), (int)Math.round(0.5*Camera.screenHeight), (int)Camera.screenWidth, (int)Camera.screenHeight);
		
		
		for (GameObject tempObj : objects) {
			threads.add(new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println("Attempted draw");
					BufferedImage frame = Compute.drawGameObject(tempObj);
					frames.add(frame);
				}
			}));
			threads.getLast().start();
		}
		
		int counter = 0;
		while (frames.size() < objects.length) {
			counter++;
			System.out.print(" ");
		}
		
		for (BufferedImage i : frames) {
			g.drawImage(i, 0,0,null);
		}
	}
	
	public static void main(String[] args) { 
        new renderer(); 
    } 
}
