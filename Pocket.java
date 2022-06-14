package Biliard;

import java.awt.Color;
import java.awt.Graphics;

public class Pocket {
	
	public Vector2D position;
	
	private static int diameter = 50;

	public Pocket(float x, float y){
		this.position = new Vector2D(x, y);
	}
	
	public void paint(Graphics g){
		g.setColor(Color.gray);
		g.fillOval((int) position.getX(),(int) position.getY(),diameter,diameter);
	}

}
