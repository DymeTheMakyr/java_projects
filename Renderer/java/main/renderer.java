package main;

import classes.classes.*;
import java.awt.*;
import java.awt.event.*;

public class renderer extends Frame	{
	
	public GameObject obj1 = new GameObject(new Vec(2,-1, 4), new Vec[] {
		new Vec(1,1,1),
		new Vec(-1,1,1),
		new Vec(-1,-1,1),
		new Vec(1,-1,1),
		new Vec(1,1,-1),
		new Vec(-1,1,-1),
		new Vec(-1,-1,-1),
		new Vec(1,-1,-1)
	});
	
	public renderer() {
		setVisible(true);
		setSize(300,300);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	public void paint(Graphics g) {
		for (int i = 0; i < obj1.points.length; i++) {
			if (obj1.points[i].z < 0) {
				g.setColor(Color.green);
				g.drawOval((int)Math.round(8*obj1.screenPoints[i].x+149),(int)Math.round(8*obj1.screenPoints[i].y+149), 4, 4);
			}
			else if (obj1.points[i].z > 0) {
				g.setColor(Color.red);
				g.drawOval((int)Math.round(8*obj1.screenPoints[i].x+149),(int)Math.round(8*obj1.screenPoints[i].y+149), 4, 4);
			}
		}
	}
	
	public static void main(String[] args) { 
        new renderer(); 
    } 
}
