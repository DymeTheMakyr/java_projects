package main;

import classes.classes.*;
import java.awt.*;
import java.awt.event.*;

public class renderer extends Frame	{
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
		g.drawRoundRect(140, 140, 20, 20, 10, 10);
	}
	
	public static void main(String[] args) 
    { 
        new renderer(); 
    } 
}
